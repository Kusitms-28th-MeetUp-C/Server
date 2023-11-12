package com.kusitms.mainservice.domain.user.auth.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.error.exception.InternalServerException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kusitms.mainservice.domain.user.auth.google.GoogleToken.createGoogleToken;
import static com.kusitms.mainservice.global.error.ErrorCode.JSON_PARSING_ERROR;

@RequiredArgsConstructor
@Component
public class GoogleAuthProvider {
    @Value("${app.google.client.id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${app.google.client.secret}")
    private String GOOGLE_CLIENT_SECRET ;
    @Value("${app.google.callback.url}")
    private String GOOGLE_REDIRECT_URI;
    private static final String GRANT_TYPE = "authorization_code";
    private static final String GOOGLE_TOKEN_URL = "https://oauth2.googleapis.com/token";
    private static final String GOOGLE_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final String HEADER_TYPE = "Authorization";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResponseEntity<String> createGetRequest(String accessToken) {
        GoogleToken googleToken = createGoogleToken(accessToken);
        String googleAccessToken = googleToken.getAccessTokenWithTokenType();
        HttpEntity<String> request = createHttpEntityFromGoogleToken(googleAccessToken);
        return restTemplate.exchange(GOOGLE_URL, HttpMethod.GET, request, String.class);
    }

    public GoogleUserInfo getGoogleUserInfoFromPlatformInfo(String platformInfo) {
        GoogleUserInfo googleUserInfo;
        try {
            googleUserInfo = objectMapper.readValue(platformInfo, GoogleUserInfo.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(JSON_PARSING_ERROR);
        }
        return googleUserInfo;
    }

    public String getGoogleOAuthToken(String code) {
        ResponseEntity<String> stringGoogleOAuthToken = createPostGoogleRequest(code);
        System.out.println(stringGoogleOAuthToken);
        GoogleOAuthToken googleOAuthToken;
        try {
            googleOAuthToken = objectMapper.readValue(stringGoogleOAuthToken.getBody(), GoogleOAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new InternalServerException(JSON_PARSING_ERROR);
        }
        return googleOAuthToken.getAccessToken();
    }

    private ResponseEntity<String> createPostGoogleRequest(String code) {
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_REDIRECT_URI);
        params.put("grant_type", GRANT_TYPE);
        return restTemplate.postForEntity(GOOGLE_TOKEN_URL, params, String.class);
    }

    private HttpEntity<String> createHttpEntityFromGoogleToken(String googleAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_TYPE, googleAccessToken);
        return new HttpEntity<>(headers);
    }
}
