package com.kusitms.socketservice.error.httpException;

import com.kusitms.socketservice.error.ErrorCode;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
