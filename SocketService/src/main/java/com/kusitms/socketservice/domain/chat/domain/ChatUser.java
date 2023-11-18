package com.kusitms.socketservice.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatUser {
    private Long sessionId;
    private String name;
    private String profile;
    private UserType type;

    public static ChatUser createChatUser(User user){
        return ChatUser.builder()
                .sessionId(user.getId())
                .name(user.getName())
                .profile(user.getProfile())
                .type(user.getUserType())
                .build();
    }
}
