package com.kusitms.socketservice.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageListResponseDto {
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static ChatMessageListResponseDto of(List<ChatMessageElementResponseDto> chatMessageList) {
        return ChatMessageListResponseDto.builder()
                .chatMessageList(chatMessageList)
                .build();
    }
}
