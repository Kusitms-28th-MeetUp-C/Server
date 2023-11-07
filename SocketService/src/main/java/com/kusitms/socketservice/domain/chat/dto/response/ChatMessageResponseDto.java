package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageResponseDto {
    private String chatName;
    private List<Long> userList;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static ChatMessageResponseDto of(Chat chat, List<ChatMessageElementResponseDto> chatMessageList) {
        return ChatMessageResponseDto.builder()
                .chatName(chat.getChatName())
                .userList(chat.getUserList())
                .chatMessageList(chatMessageList)
                .build();
    }
}
