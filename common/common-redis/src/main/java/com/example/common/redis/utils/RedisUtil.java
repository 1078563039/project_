package com.example.common.redis.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

@AllArgsConstructor
public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;

    /**
     * string 类型设置值
     * @param key
     * @param value
     */
    public void setValue(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

}
