package com.example.auth.oauth2.extension.captcha;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.example.auth.result.AuthResult;
import com.example.common.base.result.exception.BaseException;
import com.example.common.utils.contants.security.SecurityConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 验证码授权模式授权者
 *
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/9/25
 */
public class CaptchaTokenGranter extends AbstractTokenGranter {

    /**
     * 声明授权者 CaptchaTokenGranter 支持授权模式 captcha
     * 根据接口传值 grant_type = captcha 的值匹配到此授权者
     * 匹配逻辑详见下面的两个方法
     *
     * @see CompositeTokenGranter#grant(String, TokenRequest)
     * @see AbstractTokenGranter#grant(String, TokenRequest)
     */
    private static final String GRANT_TYPE = "captcha";

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, Object> redisTemplate;

    public CaptchaTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService,
                               OAuth2RequestFactory requestFactory, AuthenticationManager authenticationManager,
                               RedisTemplate<String, Object> redisTemplate) {
        super(tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

        // 验证码校验逻辑
        String validateCode = parameters.get("code");
        String uuid = parameters.get("uuid");

        if (StrUtil.isBlank(validateCode)){
            throw new BaseException(AuthResult.CAPTCHA_IS_NULL);
        }
        String validateCodeKey = SecurityConstants.VALIDATE_CODE_PREFIX + uuid;

        // 从缓存取出正确的验证码和用户输入的验证码比对
        String correctValidateCode = (String) redisTemplate.opsForValue().get(validateCodeKey);

        if (StrUtil.isBlank(correctValidateCode)){
            throw new BaseException(AuthResult.CAPTCHA_EXPIRE);
        }
        if (StrUtil.isBlank(correctValidateCode)){
            throw new BaseException(AuthResult.CAPTCHA_ERROR);
        }

        // 验证码验证通过，删除 Redis 的验证码
        redisTemplate.delete(validateCodeKey);

        String username = parameters.get("username");
        String password = parameters.get("password");

        // 移除后续无用参数
        parameters.remove("password");
        parameters.remove("code");
        parameters.remove("uuid");

        // 和密码模式一样的逻辑
        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        try {
            userAuth = this.authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException var8) {
            throw new InvalidGrantException(var8.getMessage());
        } catch (BadCredentialsException var9) {
            throw new InvalidGrantException(var9.getMessage());
        }

        if (userAuth != null && userAuth.isAuthenticated()) {
            OAuth2Request storedOAuth2Request = this.getRequestFactory().createOAuth2Request(client, tokenRequest);
            return new OAuth2Authentication(storedOAuth2Request, userAuth);
        } else {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }
    }
}
