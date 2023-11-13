package com.kusitms.socketservice.domain.chat.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Document
public class ChatContent {
    private String userName;
    private String content;
    private LocalDateTime time;
}
