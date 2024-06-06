package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjectAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAdminApplication.class, args);
    }

}
