package com.example.gateway.config.cors;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsWebFilter corsFilter() {
        // 跨域配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //设置跨域的配置信息
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许所有请求来源进行跨域
        corsConfiguration.addAllowedOrigin("*");
        // spring boot2.4以后的配置
//        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        // 允许所有头进行跨域
        corsConfiguration.addAllowedHeader("*");
        // 允许所有请求方式进行跨域
        corsConfiguration.addAllowedMethod("*");
        // 允许携带cookie进行跨域
        corsConfiguration.setAllowCredentials(true);
        //任意路径都需要跨域配置
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }
}
