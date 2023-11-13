package com.kusitms.socketservice.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Document(collection = "chat")
public class Chat {
    @Id
    private String chatId;
    @Builder.Default
    private List<Long> sessionList = new ArrayList<>();
    @Builder.Default
    private List<ChatContent> chatContentList = new ArrayList<>();

    public static Chat creatChat(Long firstSessionId, Long secondSessionId){
        Chat chat = Chat.builder().build();
        chat.addSession(firstSessionId);
        chat.addSession(secondSessionId);
        return chat;
    }

    public void addSession(Long sessionId){
        this.sessionList.add(sessionId);
    }
}
