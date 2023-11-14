package com.kusitms.socketservice.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserChatResponseDto {
    private String userName;
    private String content;
    private String time;

    public static UserChatResponseDto of(String userName, String content, LocalDateTime time) {
        return UserChatResponseDto.builder()
                .userName(userName)
                .content(content)
                .time(time.toString())
                .build();
    }
}
