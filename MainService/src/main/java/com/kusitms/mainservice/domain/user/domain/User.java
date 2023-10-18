package com.kusitms.mainservice.domain.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String picture;
    private String accessToken;
    private String refreshToken;
    public User() {
    }

    public User(String email, String name, String picture, String accessToken) {
        this.email = email;
        this.name = name;
        this.picture = picture;
        this.accessToken = accessToken;
    }
    public void updateRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
