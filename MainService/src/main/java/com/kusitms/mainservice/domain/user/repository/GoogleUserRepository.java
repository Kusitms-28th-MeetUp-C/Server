package com.kusitms.mainservice.domain.user.repository;


import com.kusitms.mainservice.domain.user.dto.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoogleUserRepository extends JpaRepository<GoogleUser,String> {
}
