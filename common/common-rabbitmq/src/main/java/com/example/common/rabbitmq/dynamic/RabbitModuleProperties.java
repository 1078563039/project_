package com.example.common.rabbitmq.dynamic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/4/4 14:51
 */
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
@Data
public class RabbitModuleProperties {

    private List<RabbitModuleInfo> modules;

}
