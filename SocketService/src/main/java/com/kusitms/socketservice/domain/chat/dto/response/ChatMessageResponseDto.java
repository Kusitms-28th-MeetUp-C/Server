package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageResponseDto {
    private String receivedUser;
    private List<Long> sessionList;
    private ChatMessageElementResponseDto message;

    public static ChatMessageResponseDto of(String receivedUser, List<Long> sessionList, ChatMessageElementResponseDto message) {
        return ChatMessageResponseDto.builder()
                .receivedUser(receivedUser)
                .sessionList(sessionList)
                .message(message)
                .build();
    }
}
