package com.kusitms.socketservice.domain.chat.repository;

import com.kusitms.socketservice.domain.chat.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySessionId(String sessionId);
}
