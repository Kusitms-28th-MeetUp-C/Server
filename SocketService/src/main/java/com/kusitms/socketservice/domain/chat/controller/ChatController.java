package com.kusitms.socketservice.domain.chat.controller;

import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageRequestDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageResponseDto;
import com.kusitms.socketservice.domain.chat.service.ChatService;
import com.kusitms.socketservice.global.config.auth.SessionId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;
    private final RedisTemplate redisTemplate;

    @MessageMapping("/chat")
    public void sendChatMessage(@SessionId final Long sessionId,
                                final ChatMessageRequestDto chatMessageRequestDto){
        final ChatMessageResponseDto responseDto = chatService.createSendMessageContent(sessionId, chatMessageRequestDto);
        redisTemplate.convertAndSend("meetingRoom", responseDto);
    }
}
