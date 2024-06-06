package com.example.common.base.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BaseResultCode implements IBaseResult {

    OK("0", "操作成功"),
    ERR("100000", "系统异常"),      //10模块   00业务   00逻辑
    AUTH("100001", "登录异常"),      //10模块   00业务   00逻辑
    ;

    private String code;
    private String msg;

}
