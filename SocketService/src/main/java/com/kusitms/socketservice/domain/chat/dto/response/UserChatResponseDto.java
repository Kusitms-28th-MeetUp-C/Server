package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatUser;
import com.kusitms.socketservice.domain.chat.domain.User;
import com.kusitms.socketservice.domain.chat.domain.UserType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserChatResponseDto {
    private Long sessionId;
    private String profile;
    private String userName;
    private UserType userType;
    private String content;
    private String time;

    public static UserChatResponseDto of(ChatUser user, String content, LocalDateTime time) {
        return UserChatResponseDto.builder()
                .sessionId(user.getSessionId())
                .profile(user.getProfile())
                .userName(user.getName())
                .content(content)
                .time(time.toString())
                .build();
    }
}
