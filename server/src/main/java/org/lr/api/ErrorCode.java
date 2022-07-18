package org.lr.api;

public enum ErrorCode{
    USER_NOT_FOUND(40401, "User is not found"),

    JWT_NOT_SUPPORTED(40301, "Jwt is not supported"),
    JWT_MALFORMED(40302, "Jwt is malformed"),
    JWT_INVALID_SIGNATURE(40303, "Jwt invalid signature"),
    JWT_IS_EXPIRED(40304, "Jwt is expired"),
    JWT_IS_NULL(40305, "Jwt is null"),
    JWT_FAIL_ANALYSE(40306, "jwt fail analyse"),
    UNKNOWN_EXCEPTION(50000, "Unknown exception"),
    NULL_POINTER_EXCEPTION(50001, "Parameter is null")
    ;


    private int code;
    private String message;

    ErrorCode(int code, String message){
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