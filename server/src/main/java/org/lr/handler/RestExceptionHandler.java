package org.lr.handler;

import org.lr.api.ErrorCode;
import org.lr.api.Result;
import org.lr.exception.NotFoundException;
import org.lr.exception.TokenException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 对自定义Exception处理
 */

@Order(0)
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handlerNotFoundException(NotFoundException e){
        return new Result(e);
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handlerTokenException(TokenException e) {
        return new Result(e);
    }
}
