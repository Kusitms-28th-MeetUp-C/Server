package com.kusitms.socketservice.domain.chat.service;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import com.kusitms.socketservice.domain.chat.domain.ChatContent;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageListRequestDto;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageRequestDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageElementResponseDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageResponseDto;
import com.kusitms.socketservice.domain.chat.dto.response.ChatMessageListResponseDto;
import com.kusitms.socketservice.domain.chat.repository.ChatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.kusitms.socketservice.domain.chat.domain.ChatContent.createChatContent;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;
    public ChatMessageResponseDto createSendMessageContent(Long sessionId, ChatMessageRequestDto chatMessageRequestDto){
        Chat chat = findFirstChatBySessions(sessionId, chatMessageRequestDto.getChatSession());
        ChatContent chatContent = createChatContent(chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getContent(), chat);
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent);
        saveChat(chat);
        return ChatMessageResponseDto.of(chatMessageRequestDto.getToUserName(), chat, chatMessage);
    }

    public ChatMessageListResponseDto getMessageList(Long sessionId, ChatMessageListRequestDto chatMessageListRequestDto){
        Chat chat = findFirstChatBySessions(sessionId, chatMessageListRequestDto.getChatSession());
        List<ChatMessageElementResponseDto> chatMessageList = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        return ChatMessageListResponseDto.of(chatMessageList);
    }

    private Chat findFirstChatBySessions(Long firstSessionId, Long secondSessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionList").all(firstSessionId, secondSessionId));
        Chat chat = mongoTemplate.findOne(query, Chat.class);
        return Objects.isNull(chat) ? Chat.creatChat(firstSessionId, secondSessionId) : chat;
    }

    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }
}
