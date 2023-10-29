package com.kusitms.mainservice.domain.user.auth.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {
    private long id;
    private KakaoAccount kakaoAccount;
    private String picture;

    @Getter
    public static class KakaoAccount {
        private KakaoProfile profile;
        private String email;

    }

    @Getter
    public static class KakaoProfile {
        private String nickname;
    }
}
