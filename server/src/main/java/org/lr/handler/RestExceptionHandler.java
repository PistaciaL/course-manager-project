package org.lr.handler;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import org.lr.api.ResultCode;
import org.lr.api.Result;
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
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestExceptionHandler {

//    @ExceptionHandler(NullPointerException.class)
    public Result handlerNullPointerException(NullPointerException e) {
        return new Result(ResultCode.FAILURE, "缺少参数");
    }

    @ExceptionHandler(MyException.class)
    public Result handlerMyException(MyException e){
        return new Result(ResultCode.FAILURE, e.getMessage());
    }

}
