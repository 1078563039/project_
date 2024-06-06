package com.example.admin.listener;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.common.sms.ali.SmsUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class SmsListener {

    @Autowired
    private SmsUtil smsUtil;

//    @RabbitListener(queues = "send.sms.ali.queue")
    public void sendSmsAli(String map, Message message, Channel channel){

        try {
            //业务逻辑
            log.info("接收消息：send.sms.ali={}", map);
            HashMap<String, String> hashMap = JSON.parseObject(map, HashMap.class);
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(hashMap.get("mobile"), "{\"checkCode\":\""+ hashMap.get("checkCode") +"\"}");
            log.info("sendSmsResponse:{}", JSON.toJSONString(sendSmsResponse));
        }catch (Exception e) {
//            try {
                log.info("异常信息：{}", e);
                //消费失败，重新入队
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
                log.info("消息消费失败：{}", map);
//            } catch (IOException ex) {
//                log.info("系统异常：{}", ex);
//                ex.printStackTrace();
//            }
        }
    }
}
