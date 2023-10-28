package com.kusitms.socketservice.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MessageRequestDto {
    private Long meetingId;
    private String messageType;
    private String message;
}
