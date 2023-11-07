package com.kusitms.socketservice.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ChatMessageElementResponseDto {
    private String fromUserName;
    private String toUserName;
    private String content;
    private String time;

    public static ChatMessageElementResponseDto of(String fromUserName, String toUserName, String content, String time) {
        return ChatMessageElementResponseDto.builder()
                .fromUserName(fromUserName)
                .toUserName(toUserName)
                .content(content)
                .time(time)
                .build();
    }
}
