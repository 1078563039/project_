package com.example.gateway.security;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.example.common.base.result.BaseResult;
import com.example.common.base.result.BaseResultCode;
import com.example.common.utils.contants.security.SecurityConstants;
import com.example.gateway.config.properties.GateWayShouldSkip;
import com.example.gateway.result.GatewayResult;
import com.example.gateway.util.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.security.PublicKey;
import java.util.Map;

@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, InitializingBean {

    @Autowired
    private PublicKey publicKey;

    @Autowired
    private GateWayShouldSkip gateWayShouldSkip;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("requestUrl:{}", exchange.getRequest().getURI());

        //过滤掉无需请求的路径
        String currentPath = exchange.getRequest().getURI().getPath();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (gateWayShouldSkip.getIgnoreUrls().stream().anyMatch(path -> antPathMatcher.match(path, currentPath))) {
            return chain.filter(exchange);
        }

        //获取请求头
        ServerHttpRequest request = exchange.getRequest();

        // 不是正确的的JWT不做解析处理
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);
        if (StrUtil.isBlank(token) || !StrUtil.startWithIgnoreCase(token, SecurityConstants.JWT_PREFIX)) {
            return chain.filter(exchange);
        }

        //过滤掉黑名单
        boolean isBlack = filterBlack(token);
        if (isBlack){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.getHeaders().set("Access-Control-Allow-Origin", "*");
            response.getHeaders().set("Cache-Control", "no-cache");
            String body = JSON.toJSONString(BaseResult.failed(GatewayResult.TOKEN_INVALID_OR_EXPIRED));
            DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
            return response.writeWith(Mono.just(buffer));
        }

        //解密JWT
        Claims claims = JWTUtils.validateJwtToken(token, publicKey);
        ServerWebExchange serverWebExchange = wrapHeader(token, exchange, claims);

        return chain.filter(serverWebExchange);
    }

    /**
     * 过滤掉黑名单中过的token（已经登出的token信息）
     * @param authorization token
     */
    private boolean filterBlack(String authorization) {
        return redisTemplate.hasKey(authorization)?true:false;
    }


    /**
     * 方法实现说明:把我们从jwt解析出来的用户信息存储到请求中
     *
     * @param serverWebExchange
     * @param claims
     * @author:smlz
     * @return: ServerWebExchange
     * @exception:
     * @date:2020/1/22 12:12
     */
    private ServerWebExchange wrapHeader(String authorization, ServerWebExchange serverWebExchange, Claims claims) {
        String userInfo = JSON.toJSONString(claims);
        log.info("userInfo: {}", userInfo);
        log.info("userToken: {}", authorization);
        String userId = claims.get("additionalInfo", Map.class).get("userId").toString();
        String mobile = claims.get("additionalInfo", Map.class).get("mobile").toString();

        String roles = claims.get(SecurityConstants.JWT_AUTHORITIES_KEY).toString();
        //向headers中放文件，记得build
        ServerHttpRequest request = serverWebExchange.getRequest().mutate()
                .header("userName", claims.get("user_name", String.class))
                .header("userId", userId)
                .header("mobile", mobile)
                .header("token", authorization)
                .header("roles", roles)
                .build();

        //将现在的request 变成 change对象
        return serverWebExchange.mutate().request(request).build();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 初始化后端路由 operations.set("POST:/admin/*", "['ADMIN', 'USER']");
         */
//        HashOperations hashOperations = redisTemplate.opsForHash();
//        hashOperations.put(GlobalConstants.URL_PERM_ROLES_KEY, "**:/admin/**", "[ADMIN, USER]");
//        hashOperations.put(GlobalConstants.URL_PERM_ROLES_KEY, "**:/auth/**", "[ADMIN, USER]");

    }
}
