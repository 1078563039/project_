package com.example.auth.config.token;

import com.example.auth.security.entity.SelfUserEntity;
import com.example.common.utils.contants.security.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DefaultTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        log.info("======执行token增强=======");

        SelfUserEntity selfUserEntity = (SelfUserEntity) authentication.getPrincipal();

        final Map<String, Object> additionalInfo = new HashMap<>();
        final Map<String, Object> retMap = new HashMap<>();

        //todo 这里暴露memberId到Jwt的令牌中,后期可以根据自己的业务需要 进行添加字段
        additionalInfo.put("userId", selfUserEntity.getUserId());
        additionalInfo.put("mobile", selfUserEntity.getMobile());
        retMap.put("additionalInfo", additionalInfo);

        List<String> roleList = selfUserEntity.getAuthorities()
                .stream()
                .map(authorities -> authorities.getAuthority().substring(SecurityConstants.AUTHORITY_PREFIX.length()))
                .collect(Collectors.toList());

        retMap.put(SecurityConstants.JWT_AUTHORITIES_KEY, String.join(",", roleList));
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(retMap);

        return accessToken;
    }
}
