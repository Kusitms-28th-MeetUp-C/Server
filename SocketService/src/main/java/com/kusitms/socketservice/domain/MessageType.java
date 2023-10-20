package com.kusitms.socketservice.domain;

import com.kusitms.socketservice.error.socketException.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.kusitms.socketservice.error.ErrorCode.MESSAGE_TYPE_NOT_FOUND;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum MessageType {
    EMOJI("emoji");

    private final String stringMessageType;

    public static MessageType getEnumMessageTypeFromString(String stringMessageType) {
        return Arrays.stream(values())
                .filter(messageType -> messageType.stringMessageType.equals(stringMessageType))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(MESSAGE_TYPE_NOT_FOUND));
    }
}
