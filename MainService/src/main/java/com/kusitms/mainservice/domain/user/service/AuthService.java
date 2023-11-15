package com.kusitms.mainservice.domain.user.service;

import com.kusitms.mainservice.domain.user.auth.PlatformUserInfo;
import com.kusitms.mainservice.domain.user.auth.RestTemplateProvider;
import com.kusitms.mainservice.domain.user.domain.Platform;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.request.UserSignInRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignUpRequestDto;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import com.kusitms.mainservice.domain.user.repository.RefreshTokenRepository;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.config.auth.JwtProvider;
import com.kusitms.mainservice.global.config.auth.TokenInfo;
import com.kusitms.mainservice.global.error.exception.ConflictException;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.kusitms.mainservice.domain.user.domain.Platform.getEnumPlatformFromStringPlatform;
import static com.kusitms.mainservice.domain.user.domain.RefreshToken.createRefreshToken;
import static com.kusitms.mainservice.global.error.ErrorCode.DUPLICATE_USER;
import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplateProvider restTemplateProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserAuthResponseDto signIn(UserSignInRequestDto userSignInRequestDto, String authToken) {
        System.out.println(userSignInRequestDto.getPlatform());
        Platform platform = getEnumPlatformFromStringPlatform(userSignInRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        User getUser = getUserByPlatformUserInfo(platformUser);
        Boolean isFistLogin = Objects.isNull(getUser.getPlatform()) ? Boolean.TRUE : Boolean.FALSE;
        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(getUser);
        updateRefreshToken(tokenInfo.getRefreshToken(), getUser);
        saveUser(getUser, platform);
        return UserAuthResponseDto.of(getUser, tokenInfo, isFistLogin);
    }

//    public UserAuthResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto, String authToken) {
//        Platform platform = getEnumPlatformFromStringPlatform(userSignUpRequestDto.getPlatform());
//        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
//        validateDuplicateUser(platform, platformUser.getId());
//        User createdUser = saveUser(platformUser, platform);
//        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(createdUser);
//        updateRefreshToken(tokenInfo.getRefreshToken(), createdUser);
//        return UserAuthResponseDto.of(createdUser, tokenInfo);
//    }

    public void signOut(Long userId) {
        User findUser = getUserFromUserId(userId);
        deleteRefreshToken(findUser);
    }

    private void deleteRefreshToken(User user) {
        user.updateRefreshToken(null);
        refreshTokenRepository.deleteById(user.getId());
    }

    private void validateDuplicateUser(Platform platform, String platformId) {
        if (userRepository.existsUserByPlatformAndPlatformId(platform, platformId))
            throw new ConflictException(DUPLICATE_USER);
    }

    private void saveUser(User createdUser, Platform platform) {
        createdUser.updatePlatform(platform);
        userRepository.save(createdUser);
    }

    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(createRefreshToken(user.getId(), refreshToken));
    }

    private TokenInfo issueAccessTokenAndRefreshToken(User user) {
        return jwtProvider.issueToken(user.getId());
    }

    private User getUserFromUserId(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private User getUserByPlatformUserInfo(PlatformUserInfo platformUserInfo) {
        return userRepository.findByPlatformId(platformUserInfo.getId())
                .orElse(User.createUser(platformUserInfo));
    }

    private PlatformUserInfo getPlatformUserInfoFromRestTemplate(Platform platform, String authToken) {
        return restTemplateProvider.getUserInfoUsingRestTemplate(platform, authToken);
    }

}
