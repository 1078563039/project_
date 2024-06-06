package com.example.auth.security.provider;

import com.example.admin.api.feign.role.SysRoleFeignApi;
import com.example.admin.api.feign.role.dto.SysRoleDto;
import com.example.auth.security.entity.SelfUserEntity;
import com.example.auth.security.service.BaseUserDetailService;
import com.example.common.base.result.BaseResult;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义登录验证
 * @author zwq
 * @date 2020-04-04
**/
@Component
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private BaseUserDetailService defaultUserDetailService;

    @Resource
    private SysRoleFeignApi sysRoleFeignApi;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在  SelfUserEntity userInfo
        SelfUserEntity userInfo = defaultUserDetailService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 我们还要判断密码是否正确，这里我们的密码使用BCryptPasswordEncoder进行加密的
        if (!new BCryptPasswordEncoder().matches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (0 == userInfo.getStatus()){
            throw new LockedException("该用户已被冻结");
        }
        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        BaseResult<List<SysRoleDto>> sysRoleDtoResult = sysRoleFeignApi.selectByUserId(userInfo.getUserId());
        for (SysRoleDto sysRoleDto: sysRoleDtoResult.getData()){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRoleDto.getRoleName()));
        }
        userInfo.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}