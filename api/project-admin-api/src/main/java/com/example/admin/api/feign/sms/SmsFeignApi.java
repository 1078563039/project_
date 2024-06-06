package com.example.admin.api.feign.sms;

import com.example.admin.api.feign.sms.fallback.SmsFeignApiFallBack;
import com.example.common.base.result.BaseResult;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "短信")
@Component
@FeignClient(value = "project-admin", contextId = "sms", path = "/admin/sms", fallback = SmsFeignApiFallBack.class)
public interface SmsFeignApi {

    @GetMapping("/sendSmsMessage")
    BaseResult<Boolean> sendSmsMessage(@RequestParam("mobile") String mobile);

}
