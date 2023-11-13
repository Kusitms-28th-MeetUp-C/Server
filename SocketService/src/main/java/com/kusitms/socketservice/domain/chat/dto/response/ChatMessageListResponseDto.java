package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChatMessageListResponseDto {
    private ChatType chatType;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static ChatMessageListResponseDto of(ChatType chatType, List<ChatMessageElementResponseDto> chatMessageList){
        return ChatMessageListResponseDto.builder()
                .chatType(chatType)
                .chatMessageList(chatMessageList)
                .build();
    }
}
