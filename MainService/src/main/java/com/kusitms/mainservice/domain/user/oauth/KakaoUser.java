package com.kusitms.mainservice.domain.user.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUser {
    private long id;
    private KakaoAccount kakao_account;

    @Getter
    public static class KakaoAccount {
        private KakaoProfile profile;
        private String email;

    }

    @Getter
    @Setter
    public static class KakaoProfile {
        private String nickname;
    }

    private String picture;

    public User toUser() {
        return User.builder()
                .email(kakao_account.getEmail())
                .name(kakao_account.getProfile().getNickname())
                .picture(picture)
                .build();
    }

}
