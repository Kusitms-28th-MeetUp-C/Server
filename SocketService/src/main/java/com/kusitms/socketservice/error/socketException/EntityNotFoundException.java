package com.kusitms.socketservice.error.socketException;

import com.kusitms.socketservice.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
