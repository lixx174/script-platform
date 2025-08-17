package com.j.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 传参合法，但业务非法所产生的异常。
 *
 * @author Jinx
 */
public class UnprocessableException extends RuntimeException {

    private final int code = HttpStatus.UNPROCESSABLE_ENTITY.value();

    public UnprocessableException(String message) {
        super(message);
    }
}
