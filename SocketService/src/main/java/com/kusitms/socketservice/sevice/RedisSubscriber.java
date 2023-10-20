package com.kusitms.socketservice.sevice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.socketservice.domain.MessageType;
import com.kusitms.socketservice.dto.request.MessageRequestDto;
import com.kusitms.socketservice.dto.response.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import static com.kusitms.socketservice.domain.MessageType.getEnumMessageTypeFromString;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            MessageRequestDto requestMessage = objectMapper.readValue(publishMessage, MessageRequestDto.class);
            sendMessageForMessageType(requestMessage);
        } catch (Exception e) {
            throw new MessageDeliveryException("Error");
        }
    }

    private void sendMessageForMessageType(MessageRequestDto requestMessage) {
        MessageType enumMessageType = getEnumMessageTypeFromString(requestMessage.getMessageType());
        switch (enumMessageType) {
            case EMOJI -> sendEmojiResponse(requestMessage);
        }
    }

    private void sendEmojiResponse(MessageRequestDto messageRequestDto) {
        MessageResponseDto messageResponse = createMessageResponse(messageRequestDto.getMeetingId(), messageRequestDto.getMessage());
        messagingTemplate.convertAndSend("/sub/meeting/" + messageResponse.getMeetingId(), messageResponse);
    }

    private MessageResponseDto createMessageResponse(Long meetingId, String message) {
        return MessageResponseDto.of(meetingId, message);
    }
}
