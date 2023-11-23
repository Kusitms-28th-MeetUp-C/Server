package com.kusitms.mainservice.domain.user.repository;


import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformId(String platformId);
}
