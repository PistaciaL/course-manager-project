package org.lr.api;

public enum ResultCode {
    SUCCESS( 200, "SUCCESS" ),//成功
    FAILURE( 4000, "FAILURE" ),//失败
    UNAUTHORIZED( 4001, "未认证或Token失效" ),//未认证（签名错误、token错误）
    USER_UNAUTHORIZED( 4002, "用户名或密码不正确" ),//未通过认证
    NOT_FOUND( 4004, "接口不存在" ),//接口不存在
    INTERNAL_SERVER_ERROR( 5000, "服务器内部错误" );//服务器内部错误
    ;


    private int code;
    private String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}