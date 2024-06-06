package com.example.gateway;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

@EnableFeignClients
@EnableConfigurationProperties
@SpringBootApplication
public class ProjectGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectGatewayApplication.class, args);
    }

}
