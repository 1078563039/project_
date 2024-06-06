package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.example.admin.api.feign")
@SpringBootApplication(scanBasePackages = "com.example")
public class ProjectAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectAuthApplication.class, args);
    }
}
