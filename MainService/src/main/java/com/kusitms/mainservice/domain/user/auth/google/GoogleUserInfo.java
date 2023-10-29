package com.kusitms.mainservice.domain.user.auth.google;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GoogleUserInfo {
    private Long id;
    private String email;
    private Boolean verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
}