package com.kusitms.mainservice.domain.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kusitms.mainservice.domain.user.oauth.GoogleUser;
import com.kusitms.mainservice.domain.user.oauth.OAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${app.google.client.id}")
    private String CLIENT_ID;

    @Value("${app.google.client.secret}")
    private String CLIENT_SECRET ;
    @Value("${app.google.callback.url}")
    private String REDIRECT_URI;
    private static final String GRANT_TYPE = "authorization_code";

    public OAuthService(RestTemplate restTemplate) {
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> createPostRequest(String code) {
        String url = "https://oauth2.googleapis.com/token";

        //MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", CLIENT_ID);
        params.put("client_secret", CLIENT_SECRET);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("grant_type", GRANT_TYPE);

        //HttpHeaders headers = new HttpHeaders();
        //headers.add("Content-Type", "application/x-www-form-urlencoded");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, params, String.class);
       // HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

//        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    return responseEntity;
    }

    public OAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
//        OAuthToken oAuthToken = null;
//        try {
//            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        return oAuthToken;
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    public ResponseEntity<String> createGetRequest(OAuthToken oAuthToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response;
    }

    public GoogleUser getUserInfo(ResponseEntity<String> userInfoResponse) throws JsonProcessingException{

//        try {
//            googleUser = objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        return objectMapper.readValue(userInfoResponse.getBody(), GoogleUser.class);
    }
}