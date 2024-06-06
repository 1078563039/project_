package com.example.admin.controller.sms;

import com.alibaba.fastjson.JSON;
import com.example.admin.api.feign.sms.SmsFeignApi;
import com.example.common.base.result.BaseResult;
import com.example.common.redis.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController implements SmsFeignApi {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendSmsMessage")
    public BaseResult<Boolean> sendSmsMessage(@RequestParam("mobile") String mobile){

        String randomNumeric = RandomStringUtils.randomNumeric(6);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkCode", randomNumeric);

        redisUtil.setValue("mobile_code_" + mobile, randomNumeric);
        rabbitTemplate.convertAndSend("send.sms.ali.exchange", "send.sms.ali.routing.key", JSON.toJSONString(map));

        return BaseResult.ok(Boolean.TRUE);
    }

}
