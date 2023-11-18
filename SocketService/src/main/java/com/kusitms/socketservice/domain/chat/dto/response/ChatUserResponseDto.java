package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatUser;
import com.kusitms.socketservice.domain.chat.domain.UserType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChatUserResponseDto {
    private Long sessionId;
    private String name;
    private UserType type;
    private String profile;

    public static ChatUserResponseDto of(ChatUser chatUser){
        return ChatUserResponseDto.builder()
                .sessionId(chatUser.getSessionId())
                .name(chatUser.getName())
                .type(chatUser.getType())
                .profile(chatUser.getProfile())
                .build();
    }
}
