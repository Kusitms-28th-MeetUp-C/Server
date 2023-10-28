package com.kusitms.socketservice.error.socketException;

import com.kusitms.socketservice.error.ErrorCode;
import lombok.Getter;
import org.springframework.messaging.MessageDeliveryException;

@Getter
public class BusinessException extends MessageDeliveryException {
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
