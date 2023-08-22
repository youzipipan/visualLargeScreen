package com.pg.screen.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author c.chuang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

}
