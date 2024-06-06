package com.example.auth.result;

import com.example.common.base.result.IBaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthResult implements IBaseResult {

    CAPTCHA_IS_NULL("110101", "验证码不存在"),
    CAPTCHA_INVALID("110102", "验证码无效"),
    CAPTCHA_ERROR("110103", "验证码有误"),
    CAPTCHA_EXPIRE("110104", "验证码过期"),


    ;

    private String code;
    private String msg;

}
