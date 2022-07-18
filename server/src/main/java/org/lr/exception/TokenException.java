package org.lr.exception;

import org.lr.api.ErrorCode;

public class TokenException extends GlobalException{

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
