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
    public ChatMessageResponseDto createSendMessageContent(Long userId, ChatMessageRequestDto chatMessageRequestDto){
        Chat chat = getChatFromChatName(chatMessageRequestDto.getChatName());
        addUserId(chat, userId);
        List<ChatMessageElementResponseDto> chatMessageList = chatMessageElementList(chat, chatMessageRequestDto);
        return ChatMessageResponseDto.of(chat, chatMessageList);
    }

    private void addUserId(Chat chat, Long userId){
        if(chat.getUserList().contains(userId)) return;
        chat.addUser(userId);
    }

    private List<ChatMessageElementResponseDto> chatMessageElementList(Chat chat, ChatMessageRequestDto chatMessageRequestDto){
        List<ChatMessageElementResponseDto> chatMessageList = chat.getChatContentList().stream()
                .map(this::createChatMessageElementResponseDtoFromContent)
                .collect(Collectors.toList());
        ChatMessageElementResponseDto newChat = createChatMessageElementResponseDto(chatMessageRequestDto);
        chatMessageList.add(newChat);
        return chatMessageList;
    }

    private ChatMessageElementResponseDto createChatMessageElementResponseDtoFromContent(ChatContent chatContent){
        return ChatMessageElementResponseDto.of(chatContent.getFomUserName(), chatContent.getToUserName(),
                chatContent.getContent(), chatContent.getTime().toString());
    }

    private ChatMessageElementResponseDto createChatMessageElementResponseDto(ChatMessageRequestDto chatMessageRequestDto){
        return ChatMessageElementResponseDto.of(chatMessageRequestDto.getFromUserName(), chatMessageRequestDto.getToUserName(),
                chatMessageRequestDto.getContent(), LocalDateTime.now().toString());
    }

    private Chat getChatFromChatName(String chatName){
        return chatRepository.findByChatName(chatName)
                .orElseThrow(() -> new EntityNotFoundException(CHATTING_NOT_FOUND));
    }
}
