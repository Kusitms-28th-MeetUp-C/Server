package com.kusitms.socketservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageResponseDto {
    private Long meetingId;
    private String message;

    public static MessageResponseDto of(Long meetingId, String message) {
        return MessageResponseDto.builder()
                .meetingId(meetingId)
                .message(message)
                .build();
    }
}
