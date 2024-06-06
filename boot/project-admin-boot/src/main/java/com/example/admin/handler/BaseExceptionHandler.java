package com.example.admin.handler;

import com.example.common.base.result.BaseResult;
import com.example.common.base.result.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 处理@Validated参数校验失败异常
     * @param exception 异常类
     * @return 响应
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult exceptionHandler(MethodArgumentNotValidException exception){
        BindingResult result = exception.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    log.warn("Bad Request Parameters: dto entity [{}],field [{}],message [{}]",fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage());
                    stringBuilder.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";\n");
                });
            }
        }
        return BaseResult.failed("400", stringBuilder.toString());
    }

    /**
     * 系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(BaseException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResult baseException(Exception e){
        BaseException baseException = (BaseException) e;
        return BaseResult.failed(baseException.getCode(), baseException.getMsg());
    }

    /**
     * 系统异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResult error(Exception e){
        e.printStackTrace();
        return BaseResult.failed();
    }

}
