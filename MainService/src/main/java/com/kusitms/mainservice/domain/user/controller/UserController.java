package com.kusitms.mainservice.domain.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.mainservice.domain.user.dto.OAuthLoginResponseDto;
import com.kusitms.mainservice.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private static final String ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    @Value("${app.google.client.id}")
    private String CLIENT_ID;
    @Value("${app.google.callback.url}")
    private String REDIRECT_URI;
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

    private final UserService userService;


    @GetMapping("/login")
    public String login() {
        return "redirect:" + ENDPOINT + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE + "&scope=" + SCOPE;
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<OAuthLoginResponseDto> oauthLogin(@RequestParam String code) throws JsonProcessingException {
        return new ResponseEntity(userService.oauthLogin(code),HttpStatus.OK);
        //return new ResponseEntity(new TokenResponse(token, "bearer"), HttpStatus.OK);

    }
}
