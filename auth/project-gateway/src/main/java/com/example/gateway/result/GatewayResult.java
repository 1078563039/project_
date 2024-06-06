package com.example.gateway.result;

import com.example.common.base.result.IBaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GatewayResult implements IBaseResult {

    TOKEN_INVALID_OR_EXPIRED("100101", "toke无效"),
    ACCESS_UNAUTHORIZED("100102", "连接拒绝"),
    TOKEN_ACCESS_FORBIDDEN("100103", "token无效"),

    TOKEN_INVALID("100104", "token无效"),
    ;

    private String code;
    private String msg;

}
