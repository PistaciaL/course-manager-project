package org.lr.handler;

import org.lr.api.ErrorCode;
import org.lr.api.Result;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局拦截Exception, 优先级最低, 避免前端直接获取到Server的错误代码
 */

@Order(Integer.MAX_VALUE)
@RestControllerAdvice
public class FinalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handlerGlobalException(Exception e) {
        return new Result(ErrorCode.UNKNOWN_EXCEPTION);
    }
}
