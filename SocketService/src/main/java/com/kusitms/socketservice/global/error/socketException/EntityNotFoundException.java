package com.kusitms.socketservice.global.error.socketException;

import com.kusitms.socketservice.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
