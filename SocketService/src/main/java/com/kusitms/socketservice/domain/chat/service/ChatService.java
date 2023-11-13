package com.kusitms.socketservice.domain.chat.service;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import com.kusitms.socketservice.domain.chat.domain.ChatContent;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageRequestDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageElementResponseDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageResponseDto;
import com.kusitms.socketservice.domain.chat.repository.ChatRepository;
import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.socketservice.global.error.ErrorCode.CHATTING_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final ChatRepository chatRepository;
    public ChatMessageResponseDto createSendMessageContent(Long sessionId, ChatMessageRequestDto chatMessageRequestDto){
        Chat chat = getChatFromSessions(sessionId, chatMessageRequestDto.getChatSession());
        List<ChatMessageElementResponseDto> chatMessageList = chatMessageElementList(chat, chatMessageRequestDto);
        return ChatMessageResponseDto.of(chat, chatMessageList);
    }

    private List<ChatMessageElementResponseDto> chatMessageElementList(Chat chat, ChatMessageRequestDto chatMessageRequestDto){
        List<ChatMessageElementResponseDto> chatMessageList
                = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        ChatMessageElementResponseDto newChat
                = ChatMessageElementResponseDto.of(chatMessageRequestDto.getUserName(), chatMessageRequestDto.getContent(), LocalDateTime.now().toString());
        chatMessageList.add(newChat);
        return chatMessageList;
    }

    private Chat getChatFromSessions(Long firstSessionId, Long secondSessionId){
        return chatRepository.findFirstBySessionListContainsAndSessionListContains(firstSessionId, secondSessionId)
                .orElse(Chat.creatChat(firstSessionId, secondSessionId));
    }
}
