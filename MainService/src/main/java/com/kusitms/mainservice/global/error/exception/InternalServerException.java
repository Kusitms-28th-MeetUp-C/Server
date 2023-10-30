package com.kusitms.mainservice.global.error.exception;

import com.kusitms.mainservice.global.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
