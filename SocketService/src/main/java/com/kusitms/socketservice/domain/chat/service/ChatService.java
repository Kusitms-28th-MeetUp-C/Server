package com.kusitms.socketservice.domain.chat.service;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import com.kusitms.socketservice.domain.chat.domain.ChatContent;
import com.kusitms.socketservice.domain.chat.domain.ChatUser;
import com.kusitms.socketservice.domain.chat.domain.User;
import com.kusitms.socketservice.domain.chat.dto.request.ChatListRequestDto;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageListRequestDto;
import com.kusitms.socketservice.domain.chat.dto.request.ChatMessageRequestDto;
import com.kusitms.socketservice.domain.chat.dto.response.*;
import com.kusitms.socketservice.domain.chat.repository.ChatRepository;
import com.kusitms.socketservice.domain.chat.repository.UserRepository;
import com.kusitms.socketservice.global.error.socketException.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.kusitms.socketservice.domain.chat.domain.ChatContent.createChatContent;
import static com.kusitms.socketservice.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {
    private final MongoTemplate mongoTemplate;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatMessageResponseDto createSendMessageContent(String sessionId, ChatMessageRequestDto chatMessageRequestDto) {
        Chat chat = getChatBySessions(sessionId, chatMessageRequestDto.getChatSession());
        ChatContent chatContent = createChatContent(chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getContent(), chat);
        ChatMessageElementResponseDto chatMessage = ChatMessageElementResponseDto.of(chatContent);
        List<String> sessionIdList = getSessionIdList(sessionId, chatMessageRequestDto.getChatSession());
        saveChat(chat);
        return ChatMessageResponseDto.of(chatMessageRequestDto.getToUserName(), sessionIdList, chatMessage);
    }

    public ChatMessageListResponseDto sendChatDetailMessage(String sessionId, ChatMessageListRequestDto chatMessageListRequestDto) {
        Chat chat = getChatBySessions(sessionId, chatMessageListRequestDto.getChatSession());
        ChatUserResponseDto chatUserResponseDto = getChatUserResponseDto(chat, chatMessageListRequestDto.getFromUserName());
        List<ChatMessageElementResponseDto> chatMessageList = ChatMessageElementResponseDto.listOf(chat.getChatContentList());
        saveChat(chat);
        return ChatMessageListResponseDto.of(chatUserResponseDto, chatMessageList);
    }

    public ChatListResponseDto sendUserChatListMessage(String sessionId, ChatListRequestDto chatListRequestDto) {
        List<Chat> chatList = findChatListBySession(sessionId);
        List<UserChatResponseDto> userChatResponseDtoList = createUserChatResponseDto(chatList, chatListRequestDto.getUserName());
        return ChatListResponseDto.of(userChatResponseDtoList);
    }

    private List<String> getSessionIdList(String firstSessionId, String secondSessionId){
        List<String> sessionList = new ArrayList<>();
        sessionList.add(firstSessionId);
        sessionList.add(secondSessionId);
        return sessionList;
    }

    private ChatUserResponseDto getChatUserResponseDto(Chat chat, String name) {
        ChatUser chatUser = getChatUserReceivedUser(chat, name);
        return ChatUserResponseDto.of(chatUser);
    }

    private List<UserChatResponseDto> createUserChatResponseDto(List<Chat> chatList, String userName) {
        return chatList.stream()
                .map(chat ->
                        UserChatResponseDto.of(
                                getChatUserReceivedUser(chat, userName),
                                getLastChatContent(chat.getChatContentList()).getContent(),
                                getLastChatContent(chat.getChatContentList()).getTime()))
                .collect(Collectors.toList());
    }

    private ChatUser getChatUserReceivedUser(Chat chat, String name) {
        if (!Objects.equals(chat.getChatUserList().get(0).getName(), name))
            return chat.getChatUserList().get(0);
        else
            return chat.getChatUserList().get(1);
    }


    private ChatContent getLastChatContent(List<ChatContent> chatContentList) {
        return chatContentList.get(chatContentList.size() - 1);
    }

    private Chat getChatBySessions(String firstSessionId, String secondSessionId) {
        Chat chat = findFirstChatBySessions(firstSessionId, secondSessionId);
        if (Objects.isNull(chat)) {
            ChatUser firstChatUser = createChatUser(firstSessionId);
            ChatUser secondChatUser = createChatUser(secondSessionId);
            return Chat.creatChat(firstChatUser, secondChatUser);
        } else
            return chat;
    }

    private ChatUser createChatUser(String sessionId) {
        User user = getUserFromSessionId(sessionId);
        return ChatUser.createChatUser(user);
    }

    private String getReceivedUserName(Chat chat, String user) {
        if (!Objects.equals(chat.getChatUserList().get(0).getName(), user))
            return chat.getChatUserList().get(0).getName();
        else
            return chat.getChatUserList().get(1).getName();
    }

    private Chat findFirstChatBySessions(String firstSessionId, String secondSessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatUserList.sessionId").all(firstSessionId, secondSessionId));
        return mongoTemplate.findOne(query, Chat.class);
    }

    private List<Chat> findChatListBySession(String sessionId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("chatUserList.sessionId").all(sessionId));
        return mongoTemplate.find(query, Chat.class);
    }

    private User getUserFromSessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    public void saveChat(Chat chat) {
        chatRepository.save(chat);
    }
}
