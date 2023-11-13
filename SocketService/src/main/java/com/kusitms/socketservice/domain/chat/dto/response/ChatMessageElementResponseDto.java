package com.kusitms.socketservice.domain.chat.dto.response;

import com.kusitms.socketservice.domain.chat.domain.ChatContent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ChatMessageElementResponseDto {
    private String userName;
    private String content;
    private String time;

    public static List<ChatMessageElementResponseDto> listOf(List<ChatContent> chatContentList){
        return chatContentList.stream()
                .map(chatContent -> ChatMessageElementResponseDto.of(chatContent.getUserName(), chatContent.getContent(), chatContent.getTime().toString()))
                .collect(Collectors.toList());
    }

    public static ChatMessageElementResponseDto of(String userName, String content, String time) {
        return ChatMessageElementResponseDto.builder()
                .userName(userName)
                .content(content)
                .time(time)
                .build();
    }
}
