package com.kusitms.mainservice.domain.user.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.kusitms.mainservice.domain.user.oauth.GoogleUser;
import com.kusitms.mainservice.domain.user.oauth.OAuthToken;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.OAuthLoginResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.domain.user.security.jwt.JwtTokenProvider;
import com.kusitms.mainservice.domain.user.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OAuthService oauthService;
    private final JwtTokenProvider jwtTokenProvider;


    public OAuthLoginResponseDto oauthLogin(String code) throws JsonProcessingException {
        ResponseEntity<String> accessTokenResponse = oauthService.createPostRequest(code);
        OAuthToken oAuthToken = oauthService.getAccessToken(accessTokenResponse);
        logger.info("Access Token: {}", oAuthToken.getAccessToken());

        ResponseEntity<String> userInfoResponse = oauthService.createGetRequest(oAuthToken);
        GoogleUser googleUser = oauthService.getUserInfo(userInfoResponse);
        logger.info("Google User Name: {}", googleUser.getName());
        if (!isJoinedUser(googleUser)) {
            signUpWithGoogleUserAndOAuthToken(googleUser, oAuthToken);
       }
        User user = userRepository.findByEmail(googleUser.getEmail()).orElseThrow();
//        return jwtTokenProvider.createAccessToken(user.getId());
        TokenInfo accessToken = jwtTokenProvider.createAccessToken(user.getId());
        TokenInfo refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        user.updateRefreshToken(refreshToken.getToken());
        return new OAuthLoginResponseDto(user.getId(), user.getEmail(), user.getPicture(), accessToken.getToken(), accessToken.getToken(), accessToken.getExpireTime(), refreshToken.getExpireTime()
        );
    }

    private boolean isJoinedUser(GoogleUser googleUser) {
        Optional<User> users = userRepository.findByEmail(googleUser.getEmail());
        logger.info("Joined User: {}", users);
        return users.isPresent();
    }

    private void signUpWithGoogleUserAndOAuthToken(GoogleUser googleUser, OAuthToken oAuthToken) {
        User user = googleUser.toUser(oAuthToken.getAccessToken());
        userRepository.save(user);
    }
}