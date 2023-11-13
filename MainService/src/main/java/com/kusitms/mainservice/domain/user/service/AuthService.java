package com.kusitms.mainservice.domain.user.service;

import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.auth.PlatformUserInfo;
import com.kusitms.mainservice.domain.user.auth.RestTemplateProvider;
import com.kusitms.mainservice.domain.user.domain.Platform;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.request.UserSignInRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignUpRequestDto;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
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
    private final TemplateRepository templateRepository;
    private final RoadmapRepository roadmapRepository;
    public UserAuthResponseDto signIn(UserSignInRequestDto userSignInRequestDto, String authToken) {
        Platform platform = getEnumPlatformFromStringPlatform(userSignInRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        User getUser = getUser(platformUser);
        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(getUser);
        updateRefreshToken(tokenInfo.getRefreshToken(), getUser);
        return UserAuthResponseDto.of(getUser, tokenInfo);
    }

    public UserAuthResponseDto signUp(UserSignUpRequestDto userSignUpRequestDto, String authToken) {
        Platform platform = getEnumPlatformFromStringPlatform(userSignUpRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        validateDuplicateUser(platform, platformUser.getId());
        User createdUser = saveUser(platformUser, platform);
        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(createdUser);
        updateRefreshToken(tokenInfo.getRefreshToken(), createdUser);
        return UserAuthResponseDto.of(createdUser, tokenInfo);
    }

    private void validateDuplicateUser(Platform platform, String platformId) {
        if (userRepository.existsUserByPlatformAndPlatformId(platform, platformId))
            throw new ConflictException(DUPLICATE_USER);
    }

    private User saveUser(PlatformUserInfo platformUser, Platform platform) {
        User createdUser = User.createUser(platformUser, platform);
        userRepository.save(createdUser);
        return createdUser;
    }

    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(createRefreshToken(user.getId(), refreshToken));
    }

    private TokenInfo issueAccessTokenAndRefreshToken(User user) {
        return jwtProvider.issueToken(user.getId());
    }

    private User getUser(PlatformUserInfo platformUserInfo) {
        return userRepository.findByPlatformId(platformUserInfo.getId())
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private PlatformUserInfo getPlatformUserInfoFromRestTemplate(Platform platform, String authToken) {
        return restTemplateProvider.getUserInfoUsingRestTemplate(platform, authToken);
    }

}
