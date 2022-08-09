package org.lr.api;

import com.sun.javafx.font.directwrite.RECT;
import lombok.Data;

@Data
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result(ResultCode code, String message){
        this.code = code.getCode();
        this.message = message;
    }

    public Result(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public Result(ResultCode code, Object data){
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public Result(ResultCode code,  String message, Object data){
        this.code = code.getCode();
        this.message = message;
        this.data = data;
    }

    public static Result success(){
        return new Result(ResultCode.SUCCESS);
    }
    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS, data);
    }
    public static Result fail(){return new Result(ResultCode.FAILURE);}
    public static Result fail(String message){
        return new Result(ResultCode.FAILURE, message);
    }
    public static Result tokenError(String message){
        return new Result(ResultCode.UNAUTHORIZED, message);
    }
    public static Result loginFail(String message){
        return new Result(ResultCode.USER_UNAUTHORIZED, message);
    }
    public static Result loginFail(){
        return new Result(ResultCode.USER_UNAUTHORIZED);
    }
    public static Result serverError(){
        return new Result(ResultCode.INTERNAL_SERVER_ERROR);
    }
}
