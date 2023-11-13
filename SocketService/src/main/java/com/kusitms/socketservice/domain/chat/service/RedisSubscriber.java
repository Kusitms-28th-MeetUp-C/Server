package com.kusitms.socketservice.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageResponseDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageListResponseDto;
import com.kusitms.socketservice.domain.chat.dto.response.SendMessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String publishMessage = getPublishMessage(message);
        ChatMessageResponseDto messageResponseDto = getChatMessageFromObjectMapper(publishMessage);
        SendMessageResponseDto sendMessageResponseDto
                = SendMessageResponseDto.of(messageResponseDto.getReceivedUser(), messageResponseDto.getMessage());
        messageResponseDto.getSessionList().forEach(sessionId -> sendChatMessage(sessionId, sendMessageResponseDto));
    }

    private ChatMessageResponseDto getChatMessageFromObjectMapper(String publishMessage){
        ChatMessageResponseDto messageResponseDto;
        try {
            messageResponseDto =  objectMapper.readValue(publishMessage, ChatMessageResponseDto.class);
        } catch (Exception e) {
            throw new MessageDeliveryException("Error");
        }
        return messageResponseDto;
    }

    private String getPublishMessage(Message message){
        return (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
    }

    private void sendChatMessage(Long sessionId, SendMessageResponseDto publishMessage){
        messagingTemplate.convertAndSend("/sub/chat/" + sessionId, publishMessage);
    }
}
