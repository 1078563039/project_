package com.example.common.sms.ali.config;

import com.example.common.sms.ali.SmsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SmsConfig {

    @Bean
    public SmsUtil smsUtil(Environment environment){
        return new SmsUtil(environment);
    }
}
