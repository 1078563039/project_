package com.example.gateway.config.properties;

import lombok.Data;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "security")
public class GateWayShouldSkip {

    @Setter
    private List<String> ignoreUrls;

}
