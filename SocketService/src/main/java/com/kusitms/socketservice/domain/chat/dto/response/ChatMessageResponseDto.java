package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageResponseDto {
    private List<Long> sessionList;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static ChatMessageResponseDto of(Chat chat, List<ChatMessageElementResponseDto> chatMessageList) {
        return ChatMessageResponseDto.builder()
                .sessionList(chat.getSessionList())
                .chatMessageList(chatMessageList)
                .build();
    }
}
