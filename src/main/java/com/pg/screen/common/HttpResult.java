package com.pg.screen.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 统一返回
 *
 * @author c.chuang
 */
@Data
public class HttpResult<T> {

    public static final int BUSINESS_ERROR_CODE = 512;

    public static final String BUSINESS_ERROR_MSG = "系统维护中，请联系管理员！";

    /**
     * 成功或者失败的状态
     */
    private Boolean success;

    /**
     * 返回的消息
     */
    private String message;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 返回异常code
     */
    private Integer code;

    public HttpResult(Boolean success, String message, T data, Integer code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public HttpResult() {
    }

    /**
     * 成功
     *
     * @param data 返回值
     * @return HttpResult
     */
    public HttpResult<T> success(T data) {
        return new HttpResult<>(true, "ok", data, HttpStatus.OK.value());
    }

    /**
     * 失败
     *
     * @param code    错误码
     * @param message 错误信息
     * @return HttpResult
     */
    public HttpResult<T> failure(int code, String message) {
        return new HttpResult<>(false, message, null, code);
    }

    /**
     * 失败-业务
     *
     * @return HttpResult
     */
    public HttpResult<T> businessFailure() {
        return new HttpResult<>(false, BUSINESS_ERROR_MSG, null, BUSINESS_ERROR_CODE);
    }


}
