package com.kusitms.mainservice.domain.user.oauth;


import com.kusitms.mainservice.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class GoogleUser {

    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String givenName;
    public String familyName;
    public String picture;
    public String locale;

    public GoogleUser() {
    }

    public GoogleUser(String id, String email, Boolean verifiedEmail, String name, String givenName, String familyName, String picture, String locale) {
        this.id = id;
        this.email = email;
        this.verifiedEmail = verifiedEmail;
        this.name = name;
        this.givenName = givenName;
        this.familyName = familyName;
        this.picture = picture;
        this.locale = locale;
    }

    public User toUser() {
        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .build();
    }



}