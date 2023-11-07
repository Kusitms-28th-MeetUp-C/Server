package com.kusitms.socketservice.domain.chat.domain;

import com.mysql.cj.protocol.x.XProtocolRowInputStream;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Document
public class ChatContent {
    private String fomUserName;
    private String toUserName;
    private String content;
    private LocalDateTime time;
}
