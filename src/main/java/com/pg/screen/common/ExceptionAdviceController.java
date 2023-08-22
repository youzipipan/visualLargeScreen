package com.pg.screen.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 捕捉全局异常
 *
 * @author c.chuang
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdviceController {

    /**
     * 其它异常
     *
     * @param e 异常
     * @return HttpResult
     */
    @ExceptionHandler(Exception.class)
    public HttpResult<String> exceptionHandler(Exception e) {
        log.error("Exception异常：", e);
        return new HttpResult<String>().failure(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
    }

    /**
     * 入参验证失败异常
     * 实体入参方式
     *
     * @param e 异常
     * @return HttpResult
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpResult<String> validHandler(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException]入参验证失败异常：", e);
        BindingResult exceptions = e.getBindingResult();
        FieldError fieldError = exceptions.getFieldError();
        if (fieldError == null) {
            return new HttpResult<String>().failure(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        }
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();
        log.error("{}字段，{}", field, message);
        return new HttpResult<String>().failure(
                HttpStatus.BAD_REQUEST.value(), message);
    }

    /**
     * 业务异常
     *
     * @param e 异常
     * @return HttpResult
     */
    @ExceptionHandler(BusinessException.class)
    public HttpResult<String> exceptionHandler(BusinessException e) {
        log.error("BusinessException异常：", e);
        return new HttpResult<String>().failure(
                HttpResult.BUSINESS_ERROR_CODE, e.getMessage());
    }


}
