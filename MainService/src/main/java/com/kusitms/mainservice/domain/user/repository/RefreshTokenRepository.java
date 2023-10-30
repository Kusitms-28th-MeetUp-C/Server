package com.kusitms.mainservice.domain.user.repository;

import com.kusitms.mainservice.domain.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}

