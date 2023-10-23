package com.kusitms.mainservice.domain.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;

import com.kusitms.mainservice.domain.user.oauth.GoogleUser;
import com.kusitms.mainservice.domain.user.oauth.KakaoUser;
import com.kusitms.mainservice.domain.user.oauth.OAuthToken;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.OAuthLoginResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;

import com.kusitms.mainservice.global.config.security.jwt.JwtTokenProvider;
import com.kusitms.mainservice.global.config.security.jwt.entity.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OAuthService oauthService;
    private final JwtTokenProvider jwtTokenProvider;


    public OAuthToken getOauthTokenWithCode(String socialLoginType, String code) throws JsonProcessingException{
        ResponseEntity<String> accessTokenResponse = null;
        if ("kakao".equals(socialLoginType)) {
            accessTokenResponse = oauthService.createPostKakaoRequest(code);
        } else if ("google".equals(socialLoginType)) {
            accessTokenResponse = oauthService.createPostGoogleRequest(code);
        } else {
            // socialLoginType이 지원되지 않는 경우 예외 처리
            throw new IllegalArgumentException("오류: " + socialLoginType);
        }
        OAuthToken oAuthToken = oauthService.getAccessToken(accessTokenResponse);
        logger.info("Access Token: {}", oAuthToken.getAccessToken());
        logger.info("refresh Token: {}", oAuthToken.getRefreshToken());
        logger.info("id Token: {}", oAuthToken.getIdToken());
        logger.info(" TokenType: {}", oAuthToken.getTokenType());
        logger.info("expriesin: {}", oAuthToken.getExpiresIn());
        logger.info("scope: {}", oAuthToken.getScope());
        return oAuthToken;
    }
    public OAuthLoginResponseDto oauthLoginWithToken(String socialLoginType, OAuthToken oAuthToken) throws JsonProcessingException {


        ResponseEntity<String> userInfoResponse = oauthService.createGetRequest(socialLoginType, oAuthToken);
        Object socialUser;
        if ("kakao".equals(socialLoginType)) {
            socialUser = oauthService.getKakaoUserInfo(userInfoResponse);
        } else if ("google".equals(socialLoginType)) {
            socialUser = oauthService.getGoogleUserInfo(userInfoResponse);
        } else {
            // socialLoginType이 지원되지 않는 경우 예외 처리
            throw new IllegalArgumentException("오류: " + socialLoginType);
        }

        if (socialUser instanceof KakaoUser) {
            KakaoUser kakaoUser = (KakaoUser) socialUser;
            // KakaoUser 처리 로직
            if (!isJoinedUser(kakaoUser,"kakao")) {
                signUpWithSocialUserandOauthToken(kakaoUser, oAuthToken);
            }
            User user = userRepository.findByEmail(kakaoUser.getKakao_account().getEmail()).orElseThrow();
            TokenInfo accessToken = jwtTokenProvider.createAccessToken(user.getId());
            TokenInfo refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
            user.updateRefreshToken(refreshToken.getToken());
            return new OAuthLoginResponseDto(user.getId(), user.getEmail(), user.getPicture(), accessToken.getToken(), refreshToken.getToken(), accessToken.getExpireTime(), refreshToken.getExpireTime()
            );
        } else if (socialUser instanceof GoogleUser) {
            GoogleUser googleUser = (GoogleUser) socialUser;
            // GoogleUser 처리 로직
            if (!isJoinedUser(googleUser,"google")) {
                signUpWithSocialUserandOauthToken(googleUser, oAuthToken);
            }
            User user = userRepository.findByEmail(googleUser.getEmail()).orElseThrow();
            TokenInfo accessToken = jwtTokenProvider.createAccessToken(user.getId());
            TokenInfo refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
            user.updateRefreshToken(refreshToken.getToken());
            return new OAuthLoginResponseDto(user.getId(), user.getEmail(), user.getPicture(), accessToken.getToken(), refreshToken.getToken(), accessToken.getExpireTime(), refreshToken.getExpireTime()
            );
        }
        else {
            throw new IllegalArgumentException("로그인 실패: " + socialLoginType);
        }

    }

    private boolean isJoinedUser(Object socialUser, String socialLoginType) {
        if ("kakao".equals(socialLoginType) && socialUser instanceof KakaoUser) {
            KakaoUser kakaoUser = (KakaoUser) socialUser;
            Optional<User> users = userRepository.findByEmail(kakaoUser.getKakao_account().getEmail());
            logger.info("Joined User: {}", users);
            return users.isPresent();
        } else if ("google".equals(socialLoginType) && socialUser instanceof GoogleUser) {
            GoogleUser googleUser = (GoogleUser) socialUser;
            Optional<User> users = userRepository.findByEmail(googleUser.getEmail());
            logger.info("Joined User: {}", users);
            return users.isPresent();
        } else {
            // socialUser가 지원되지 않는 경우 예외 처리
            throw new IllegalArgumentException("오류: " + socialLoginType);
        }
    }


    private void signUpWithSocialUserandOauthToken(Object socialUser, OAuthToken oAuthToken) {
        User user;
        if (socialUser instanceof KakaoUser) {
            KakaoUser kakaoUser = (KakaoUser) socialUser;
            user = kakaoUser.toUser();
        } else if (socialUser instanceof GoogleUser) {
            GoogleUser googleUser = (GoogleUser) socialUser;
            user = googleUser.toUser();
        } else {
            throw new IllegalArgumentException("오류: " + socialUser.getClass().getName());
        }
        userRepository.save(user);
    }


}