package com.kusitms.socketservice.domain.chat.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SendMessageResponseDto {
    private List<ChatMessageElementResponseDto> chatMessageList;

    public static SendMessageResponseDto of(List<ChatMessageElementResponseDto> chatMessageList){
        return SendMessageResponseDto.builder()
                .chatMessageList(chatMessageList)
                .build();
    }
}
