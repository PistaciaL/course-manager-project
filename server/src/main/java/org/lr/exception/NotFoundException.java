package org.lr.exception;

import org.lr.api.ErrorCode;

/**
 * 404-NotFound 自定义异常类
 */

public class NotFoundException extends GlobalException{


    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
