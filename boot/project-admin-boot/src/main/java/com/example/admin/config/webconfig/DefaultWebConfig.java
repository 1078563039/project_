package com.example.admin.config.webconfig;

import com.example.admin.Interceptor.UserContentInterceptor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class DefaultWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserContentInterceptor userContentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userContentInterceptor)
                .addPathPatterns("/**");
    }
}
