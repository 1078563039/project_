package com.example.gateway.config.kaptcha.handler;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.util.IdUtil;
import com.example.common.base.result.BaseResult;
import com.example.common.utils.contants.security.SecurityConstants;
import com.google.code.kaptcha.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2021/10/4
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaptchaHandler implements HandlerFunction<ServerResponse> {

    private final Producer producer;
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        // 生成验证码
        String capText = producer.createText();
        String capStr = capText.substring(0, capText.lastIndexOf("@"));
        String code = capText.substring(capText.lastIndexOf("@") + 1);
        BufferedImage image = producer.createImage(capStr);
        // 缓存验证码至Redis
        String uuid = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(SecurityConstants.VALIDATE_CODE_PREFIX + uuid, code, 600, TimeUnit.SECONDS);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            return Mono.error(e);
        }

        java.util.Map resultMap = new HashMap<String, String>();
        resultMap.put("uuid", uuid);
        resultMap.put("img", Base64.encode(os.toByteArray()));

        log.info("图片验证码：uuid={}, code={}", uuid, code);

        return ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(BaseResult.ok(resultMap)));
    }
}
