package com.kusitms.socketservice.error.socketException;

import com.kusitms.socketservice.error.ErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
