package com.kusitms.mainservice.domain.user.service;



import com.fasterxml.jackson.core.JsonProcessingException;

import com.kusitms.mainservice.domain.user.auth.GoogleOauthToken;
import com.kusitms.mainservice.domain.user.dto.GoogleUser;
import com.kusitms.mainservice.domain.user.repository.GoogleUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final GoogleOauth googleOauth;
    private final GoogleUserRepository googleUserRepository;

    private final HttpServletResponse response;

    public void request(String socialLoginType) throws IOException{
        String redirectURL = googleOauth.getOauthRedirectURL();
        response.sendRedirect(redirectURL);
    }

    public void oauthLogin(String socialLoginType, String code) throws JsonProcessingException {

        ResponseEntity<String> accessTokenResponse = googleOauth.requestAccessToken(code);
        GoogleOauthToken OAuthToken = googleOauth.getAccessToken(accessTokenResponse);
        ResponseEntity<String> userInfoResponse = googleOauth.requestUserInfo(OAuthToken);
        GoogleUser googleUser = googleOauth.getUserInfo(userInfoResponse);
        googleUserRepository.save(googleUser);

    }

}
