package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SendMessageResponseDto {
    private ChatType chatType;
    private String receivedUser;
    private ChatMessageElementResponseDto message;

    public static SendMessageResponseDto of(ChatType chatType, String receivedUser, ChatMessageElementResponseDto message) {
        return SendMessageResponseDto.builder()
                .chatType(chatType)
                .receivedUser(receivedUser)
                .message(message)
                .build();
    }
}
