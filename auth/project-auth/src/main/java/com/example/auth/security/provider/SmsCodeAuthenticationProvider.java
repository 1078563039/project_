package com.example.auth.security.provider;

import cn.hutool.core.util.StrUtil;
import com.example.admin.api.feign.role.SysRoleFeignApi;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.auth.security.entity.SelfUserEntity;
import com.example.auth.security.service.SmsCodeDetailsService;
import com.example.auth.security.token.SmsCodeAuthenticationToken;
import com.example.common.base.result.BaseResult;
import com.example.common.utils.contants.security.SecurityConstants;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 短信验证码认证授权提供者
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/9/25
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private SmsCodeDetailsService userDetailsService;

    private SysRoleFeignApi sysRoleFeignApi;

    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String code = request.getParameter("mobilecode");

        if (!code.equals("666666")) { // 666666 是后门，因为短信收费，正式环境删除这个if分支
            String codeKey = SecurityConstants.VALIDATE_CODE_SMS_PREFIX + mobile;
            String correctCode = redisTemplate.opsForValue().get(codeKey);
            // 验证码比对
            if (StrUtil.isBlank(correctCode) || !code.equals(correctCode)) {
                throw new RuntimeException("验证码不正确");
            }
            // 比对成功删除缓存的验证码
            redisTemplate.delete(codeKey);
        }
        SelfUserEntity userInfo = userDetailsService.loadUserByUsername(mobile);
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        BaseResult<List<SysRoleDto>> sysRoleDtoResult = sysRoleFeignApi.selectByUserId(userInfo.getUserId());
        for (SysRoleDto sysRoleDto: sysRoleDtoResult.getData()){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleDto.getRoleName()));
        }
        userInfo.setAuthorities(authorities);
        SmsCodeAuthenticationToken result = new SmsCodeAuthenticationToken(userInfo, authentication.getCredentials(), authorities);
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
