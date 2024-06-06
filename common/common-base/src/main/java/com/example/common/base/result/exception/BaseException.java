package com.example.common.base.result.exception;

import com.example.common.base.result.IBaseResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {

    private String code;
    private String msg;

    public BaseException() {
    }

    public BaseException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseException(IBaseResult baseResult) {
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }

    public BaseException(String message, IBaseResult baseResult) {
        super(message);
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }

    public BaseException(Throwable cause, IBaseResult baseResult) {
        super(cause);
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, IBaseResult baseResult) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = baseResult.getCode();
        this.msg = baseResult.getMsg();
    }
}
