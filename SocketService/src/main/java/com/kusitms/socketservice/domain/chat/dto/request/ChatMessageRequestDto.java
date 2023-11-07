package com.kusitms.socketservice.domain.chat.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ChatMessageRequestDto {
    private String chatName;
    private String fromUserName;
    private String toUserName;
    private String content;
}
