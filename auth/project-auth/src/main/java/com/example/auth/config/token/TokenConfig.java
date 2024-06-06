package com.example.auth.config.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
public class TokenConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //jwt对称加密
        //jwtAccessTokenConverter.setSigningKey("dzk");

        //jwt非对称加密
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("oauth2.jks"), "oauth2".toCharArray());
        jwtAccessTokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2", "oauth2".toCharArray()));
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore(){
        //采用redis存储token策略
        //return new RedisTokenStore(redisConnectionFactory);
        //采用jdbc存储token策略
        //return new JdbcTokenStore(dataSource);
        //采用JWT的token存储策略
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

}
