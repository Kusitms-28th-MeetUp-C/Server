package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatUser;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatUserResponseDto {
    private String sessionId;
    private String name;
    private String type;
    private String profile;

    public static ChatUserResponseDto of(ChatUser chatUser){
        return ChatUserResponseDto.builder()
                .sessionId(chatUser.getSessionId())
                .name(chatUser.getName())
                .type(chatUser.getType().getStringUserType())
                .profile(chatUser.getProfile())
                .build();
    }
}
