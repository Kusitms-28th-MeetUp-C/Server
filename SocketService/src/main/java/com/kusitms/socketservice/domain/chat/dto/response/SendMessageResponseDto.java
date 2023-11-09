package com.kusitms.socketservice.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SendMessageResponseDto {
    private String chatName;
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static SendMessageResponseDto of(String chatName, List<ChatMessageElementResponseDto> chatMessageList){
        return SendMessageResponseDto.builder()
                .chatName(chatName)
                .chatMessageList(chatMessageList)
                .build();
    }
}
