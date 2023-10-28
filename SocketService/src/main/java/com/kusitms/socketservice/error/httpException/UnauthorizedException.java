package com.kusitms.socketservice.error.httpException;

import com.kusitms.socketservice.error.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

