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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kusitms.socketservice.domain.chat.domain.ChatContent.createChatContent;
import static com.kusitms.socketservice.global.error.ErrorCode.CHATTING_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;
    public ChatMessageResponseDto createSendMessageContent(Long sessionId, ChatMessageRequestDto chatMessageRequestDto){
        Chat chat = findFirstChatBySessions(sessionId, chatMessageRequestDto.getChatSession());
        createChatContent(chatMessageRequestDto.getUserName(), chatMessageRequestDto.getContent(), chat);
        List<ChatMessageElementResponseDto> chatMessageList = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        saveChat(chat);
        return ChatMessageResponseDto.of(chat, chatMessageList);
    }

    public Chat findFirstChatBySessions(Long firstSessionId, Long secondSessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionList").all(firstSessionId, secondSessionId));
        Chat chat = mongoTemplate.findOne(query, Chat.class);
        return Objects.isNull(chat) ? Chat.creatChat(firstSessionId, secondSessionId) : chat;
    }

    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }
}
