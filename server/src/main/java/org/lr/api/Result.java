package org.lr.api;

import org.lr.exception.GlobalException;

public class Result {

    private int code;
    private String message;

    public Result(int code, String message){
        this.code = code;
        this.message = message;
    }

    public Result(ErrorCode code, String message){
        this.code = code.getCode();
        this.message = message;
    }

    public Result(ErrorCode e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public Result(GlobalException e) {
        this.code = e.getCode();
        this.message = e.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
