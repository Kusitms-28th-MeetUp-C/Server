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
    private List<String> nameList = new ArrayList<>();
    @Builder.Default
    private List<ChatContent> chatContentList = new ArrayList<>();

    public static Chat creatChat(Long firstSessionId, Long secondSessionId, String firstName, String secondName) {
        Chat chat = Chat.builder().build();
        chat.addName(firstName);
        chat.addName(secondName);
        chat.addSession(firstSessionId);
        chat.addSession(secondSessionId);
        return chat;
    }

    public void addChatContent(ChatContent content) {
        this.chatContentList.add(content);
    }

    public void addName(String name) {
        this.nameList.add(name);
    }

    public void addSession(Long sessionId) {
        this.sessionList.add(sessionId);
    }
}
