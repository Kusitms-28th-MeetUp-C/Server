package com.kusitms.socketservice.global.error.httpException;

import com.kusitms.socketservice.global.error.ErrorCode;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
