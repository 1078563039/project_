package com.example.admin.api.feign.sms.fallback;

import com.example.admin.api.feign.sms.SmsFeignApi;
import com.example.common.base.result.BaseResult;
import org.springframework.stereotype.Component;

@Component
public class SmsFeignApiFallBack implements SmsFeignApi {
    @Override
    public BaseResult<Boolean> sendSmsMessage(String mobile) {
        return null;
    }
}
