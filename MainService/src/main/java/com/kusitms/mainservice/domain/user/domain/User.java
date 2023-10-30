package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.domain.user.auth.PlatformUserInfo;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private Platform platform;
    private String platformId;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;

    public static User createUser(PlatformUserInfo platformUserInfo) {
        return User.builder()
                .platformId(platformUserInfo.getId())
                .email(platformUserInfo.getEmail())
                .name(platformUserInfo.getName())
                .profile(platformUserInfo.getPicture())
                .build();
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
