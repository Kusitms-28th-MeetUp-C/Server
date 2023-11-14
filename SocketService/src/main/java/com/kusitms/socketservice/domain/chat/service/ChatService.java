package com.kusitms.socketservice.domain.chat.service;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import com.kusitms.socketservice.domain.chat.domain.ChatContent;
import com.kusitms.socketservice.domain.chat.dto.request.ChatListRequestDto;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageListRequestDto;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageRequestDto;
import com.kusitms.socketservice.domain.chat.dto.response.*;
import com.kusitms.socketservice.domain.chat.repository.ChatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kusitms.socketservice.domain.chat.domain.ChatContent.createChatContent;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;
    public ChatMessageResponseDto createSendMessageContent(Long sessionId, ChatMessageRequestDto chatMessageRequestDto){
        Chat chat = getChatBySessions(sessionId, chatMessageRequestDto.getChatSession(),
                chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getToUserName());
        ChatContent chatContent = createChatContent(chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getContent(), chat);
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent);
        saveChat(chat);
        return ChatMessageResponseDto.of(chatMessageRequestDto.getToUserName(), chat, chatMessage);
    }

    public ChatMessageListResponseDto sendChatDetailMessage(Long sessionId, ChatMessageListRequestDto chatMessageListRequestDto){
        Chat chat = getChatBySessions(sessionId, chatMessageListRequestDto.getChatSession(),
                chatMessageListRequestDto.getFromUserName(), chatMessageListRequestDto.getToUserName());
        List<ChatMessageElementResponseDto> chatMessageList = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        saveChat(chat);
        return ChatMessageListResponseDto.of(chatMessageList);
    }

    public ChatListResponseDto sendUserChatListMessage(Long sessionId, ChatListRequestDto chatListRequestDto){
        List<Chat> chatList = findChatListBySession(sessionId);
        List<UserChatResponseDto> userChatResponseDtoList = createUserChatResponseDto(chatList, chatListRequestDto.getUserName());
        return ChatListResponseDto.of(userChatResponseDtoList);
    }

    private List<UserChatResponseDto> createUserChatResponseDto(List<Chat> chatList, String user){
        return chatList.stream()
                .map(chat ->
                        UserChatResponseDto.of(
                                getReceivedUserName(chat, user),
                                getLastChatContent(chat.getChatContentList()).getContent(),
                                getLastChatContent(chat.getChatContentList()).getTime()))
                .collect(Collectors.toList());
    }

    private ChatContent getLastChatContent(List<ChatContent> chatContentList){
        return chatContentList.get(chatContentList.size() - 1);
    }

    private Chat getChatBySessions(Long firstSessionId, Long secondSessionId, String firstUser, String secondUser) {
        Chat chat = findFirstChatBySessions(firstSessionId, secondSessionId);
        if(Objects.isNull(chat))
            return Chat.creatChat(firstSessionId, secondSessionId, firstUser, secondUser);
        else
            return chat;
    }

    private String getReceivedUserName(Chat chat, String user){
        if(!Objects.equals(chat.getNameList().get(0), user))
            return chat.getNameList().get(0);
        else
            return chat.getNameList().get(1);
    }

    private Chat findFirstChatBySessions(Long firstSessionId, Long secondSessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionList").all(firstSessionId, secondSessionId));
        return mongoTemplate.findOne(query, Chat.class);
    }

    private List<Chat> findChatListBySession(Long sessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sessionList").all(sessionId));
        return mongoTemplate.find(query, Chat.class);
    }

    public void saveChat(Chat chat){
        chatRepository.save(chat);
    }
}
