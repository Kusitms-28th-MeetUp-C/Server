package com.kusitms.socketservice.global.error.httpException;

import com.kusitms.socketservice.global.error.ErrorCode;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

