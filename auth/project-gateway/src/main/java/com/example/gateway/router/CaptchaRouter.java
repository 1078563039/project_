package com.example.gateway.router;

import com.example.gateway.config.kaptcha.handler.CaptchaHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * 图片验证码路由
 */
@Configuration
public class CaptchaRouter {

    @Bean
    public RouterFunction<ServerResponse> routeFunction(CaptchaHandler captchaHandler) {
        return RouterFunctions
                .route(RequestPredicates.POST("/captcha")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), captchaHandler::handle);
    }

}
