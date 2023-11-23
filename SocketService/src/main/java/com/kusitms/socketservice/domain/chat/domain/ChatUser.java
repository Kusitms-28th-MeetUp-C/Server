package com.kusitms.socketservice.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatUser {
    private String sessionId;
    private String name;
    private String profile;
    private UserType type;

    public static ChatUser createChatUser(User user) {
        return ChatUser.builder()
                .sessionId(user.getSessionId())
                .name(user.getName())
                .profile(user.getProfile())
                .type(user.getUserType())
                .build();
    }
}
