package com.kusitms.mainservice.domain.user.repository;


import com.kusitms.mainservice.domain.user.domain.Platform;
import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByPlatformId(String platformId);

    boolean existsUserByPlatformAndPlatformId(Platform platform, String platformId);
}
