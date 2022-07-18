package org.lr.exception;

import org.lr.api.ErrorCode;

/**
 * 自定义异常类父类
 */

public class GlobalException extends Exception{
    private int code;

    public GlobalException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public GlobalException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
