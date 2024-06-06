package com.example.auth.security.config;

import com.example.admin.api.feign.role.SysRoleFeignApi;
import com.example.auth.security.provider.DefaultAuthenticationProvider;
import com.example.auth.security.provider.SmsCodeAuthenticationProvider;
import com.example.auth.security.service.SmsCodeDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 1.security高版本默认不开启basic和formLogin服务，需要手动开启
 */
@Slf4j
@Configuration
@EnableWebSecurity
@Order(1)
public class DefaultSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DefaultAuthenticationProvider defaultAuthenticationProvider;
//
    @Autowired
    private SmsCodeDetailsService smsCodeDetailsService;
//
    @Autowired
    private SysRoleFeignApi sysRoleFeignApi;
//
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //这里可启用我们自己的登陆验证逻辑
        auth.authenticationProvider(defaultAuthenticationProvider).
                authenticationProvider(smsCodeAuthenticationProvider());
    }

    //AuthenticationManager对象在OAuth2认证服务中要使用，提前放入IOC容器中
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/oauth/**", "/sms-code", "/actuator/**").permitAll()
                // @link https://gitee.com/xiaoym/knife4j/issues/I1Q5X6 (接口文档knife4j需要放行的规则)
                .antMatchers("/webjars/**", "/doc.html", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();
    }


    /**
     * 手机验证码认证授权提供者
     *
     * @return
     */
    @Bean
    public SmsCodeAuthenticationProvider smsCodeAuthenticationProvider() {
        SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setUserDetailsService(smsCodeDetailsService);
        provider.setSysRoleFeignApi(sysRoleFeignApi);
        provider.setRedisTemplate(redisTemplate);
        return provider;
    }
}
