package com.example.common.base.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: BaseResult
 * @description: 基础返回
 * @author: dengzhikai
 * @create: 2023/05/11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> implements Serializable {

    private String code;
    private String msg;
    private T data;

    /**
     * 默认返回成功
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> ok(){
        return ok(null);
    }

    /**
     * 默认返回成功并自定义返回值
     * @param t
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> ok(T t){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(BaseResultCode.OK.getCode());
        baseResult.setMsg(BaseResultCode.OK.getMsg());
        baseResult.setData(t);
        return baseResult;
    }

    /**
     * 默认返回失败
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> failed(){
        return failed(BaseResultCode.ERR);
    }

    /**
     * 默认返回失败并自定义code
     * @param iBaseResult
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> failed(IBaseResult iBaseResult){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(iBaseResult.getCode());
        baseResult.setMsg(iBaseResult.getMsg());
        return baseResult;
    }

    /**
     * 自定义返回失败并自定义code
     * @param code
     * @param desc
     * @param <T>
     * @return
     */
    public static <T> BaseResult<T> failed(String code, String desc){
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setCode(code);
        baseResult.setMsg(desc);
        return baseResult;
    }
}
