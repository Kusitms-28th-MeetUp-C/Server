package com.kusitms.socketservice.domain.chat.repository;

import com.kusitms.socketservice.domain.chat.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepository extends MongoRepository<Chat, String> {
}
