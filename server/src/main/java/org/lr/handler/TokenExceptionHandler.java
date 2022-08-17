package org.lr.handler;

import org.lr.api.Result;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenExceptionHandler {

    @ExceptionHandler(TokenException.class)
    public Result handlerTokenException(TokenException e){
        return Result.tokenError(e.getMessage());
    }
}
