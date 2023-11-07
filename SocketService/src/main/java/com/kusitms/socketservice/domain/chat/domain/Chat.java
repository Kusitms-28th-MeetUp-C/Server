package com.kusitms.socketservice.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Document(collection = "chat")
public class Chat {
    @Id
    private String chatId;
    private String chatName;
    private List<Long> userList;
    @Builder.Default
    private List<ChatContent> chatContentList = new ArrayList<>();

    public void addUser(Long userId){
        this.userList.add(userId);
    }
}
