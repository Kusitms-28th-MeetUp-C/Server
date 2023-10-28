package com.kusitms.socketservice.controller;

import com.kusitms.socketservice.dto.request.MessageRequestDto;
import com.kusitms.socketservice.error.socketException.BusinessException;
import com.kusitms.socketservice.sevice.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MessageController {
    private final SimpMessagingTemplate template;
    private final MessageService messageService;

    @MessageMapping("/message/emoji")
    public void emojiMessage(@Header("Authorization") final String authorization, final MessageRequestDto messageRequestDto, StompHeaderAccessor stompHeaderAccessor) {
        messageService.sendEmojiMessageResponse(messageRequestDto);
        template.convertAndSend("/sub/meeting/" + messageRequestDto.getMeetingId(), messageRequestDto);
    }

    @MessageExceptionHandler
    public String handleException(BusinessException exception, MessageRequestDto messageRequestDto) {
        return exception.getErrorCode().getMessage();
//        template.convertAndSend("/sub/meeting/" + messageRequestDto.getMeetingId(), exception.getErrorCode().getMessage());
    }
}