package com.kusitms.socketservice.domain.chat.repository;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatRepository extends MongoRepository<Chat, String> {
    Optional<Chat> findFirstBySessionListContainsAndSessionListContains(Long sessionId1, Long sessionId2);
}
