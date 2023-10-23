package com.kusitms.mainservice.domain.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.mainservice.domain.user.dto.OAuthLoginResponseDto;
import com.kusitms.mainservice.domain.user.oauth.OAuthToken;
import com.kusitms.mainservice.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private static final String ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String KAKAO_ENDPOINT = "https://kauth.kakao.com/oauth/authorize";
    @Value("${app.google.client.id}")
    private String CLIENT_ID;
    @Value("${app.google.callback.url}")
    private String REDIRECT_URI;
    private static final String RESPONSE_TYPE = "code";
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";

    private static final String KAKAO_SCOPE = "account_email profile_nickname";

    private final UserService userService;

    @Value("${app.kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${app.kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET ;
    @Value("${app.kakao.callback.url}")
    private String KAKAO_REDIRECT_URI;

    @GetMapping("/login")
    public String login() {
        return "redirect:" + ENDPOINT + "?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE + "&scope=" + SCOPE;
    }
    @GetMapping("/login/kakao")
    public String kakaologin() {
        return "redirect:" + KAKAO_ENDPOINT + "?client_id=" + KAKAO_CLIENT_ID + "&redirect_uri=" + KAKAO_REDIRECT_URI
                + "&response_type=" + RESPONSE_TYPE + "&scope=" + KAKAO_SCOPE;
    }

    @GetMapping("/oauth/{socialLoginType}/callback/code")
    public ResponseEntity<OAuthLoginResponseDto> oauthLoginwithtoken(@PathVariable String socialLoginType, @RequestParam String code) throws JsonProcessingException {
        return new ResponseEntity(userService.oauthLoginWithToken( socialLoginType,userService.getOauthTokenWithCode(socialLoginType,code)),HttpStatus.OK);
        //return new ResponseEntity(new TokenResponse(token, "bearer"), HttpStatus.OK);

    }
    @PostMapping("/oauth/{socialLoginType}/callback/token")
    public ResponseEntity<OAuthLoginResponseDto> oauthLoginwithtoken(@PathVariable String socialLoginType, @RequestBody OAuthToken oAuthToken) throws JsonProcessingException {
        return new ResponseEntity(userService.oauthLoginWithToken( socialLoginType,oAuthToken),HttpStatus.OK);
        //return new ResponseEntity(new TokenResponse(token, "bearer"), HttpStatus.OK);

    }
}
