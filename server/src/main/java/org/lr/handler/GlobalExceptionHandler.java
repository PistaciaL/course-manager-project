package org.lr.handler;

import org.apache.ibatis.jdbc.Null;
import org.lr.api.ErrorCode;
import org.lr.api.Result;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局Exception子类处理
 */
@Order(Integer.MAX_VALUE-1)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handlerNullPointerException(Exception e) {
        return new Result(ErrorCode.NULL_POINTER_EXCEPTION);
    }
}
