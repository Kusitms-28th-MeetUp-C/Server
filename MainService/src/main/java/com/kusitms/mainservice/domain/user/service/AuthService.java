package com.kusitms.mainservice.domain.user.service;

import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.repository.TeamRepository;
import com.kusitms.mainservice.domain.user.auth.PlatformUserInfo;
import com.kusitms.mainservice.domain.user.auth.RestTemplateProvider;
import com.kusitms.mainservice.domain.user.domain.Platform;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.domain.UserType;
import com.kusitms.mainservice.domain.user.dto.request.UserSignInRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignUpRequestDto;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import com.kusitms.mainservice.domain.user.repository.RefreshTokenRepository;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.config.auth.JwtProvider;
import com.kusitms.mainservice.global.config.auth.TokenInfo;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

import static com.kusitms.mainservice.domain.user.domain.Platform.getEnumPlatformFromStringPlatform;
import static com.kusitms.mainservice.domain.user.domain.RefreshToken.createRefreshToken;
import static com.kusitms.mainservice.domain.user.domain.UserType.getEnumUserTypeFromStringUserType;
import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final JwtProvider jwtProvider;
    private final RestTemplateProvider restTemplateProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public UserAuthResponseDto signIn(UserSignInRequestDto userSignInRequestDto, String authToken) {
        Platform platform = getEnumPlatformFromStringPlatform(userSignInRequestDto.getPlatform());
        PlatformUserInfo platformUser = getPlatformUserInfoFromRestTemplate(platform, authToken);
        User getUser = getUserByPlatformUserInfo(platformUser, platform);
        Boolean isFistLogin = Objects.isNull(getUser.getUserType()) ? Boolean.TRUE : Boolean.FALSE;
        TokenInfo tokenInfo = issueAccessTokenAndRefreshToken(getUser);
        updateRefreshToken(tokenInfo.getRefreshToken(), getUser);
        saveUser(getUser);
        return UserAuthResponseDto.of(getUser, tokenInfo, isFistLogin);
    }

    public void signUp(Long userId, UserSignUpRequestDto userSignUpRequestDto) {
        User user = getUserFromUserId(userId);
        UserType userType = getEnumUserTypeFromStringUserType(userSignUpRequestDto.getUserType());
        user.updateSignUpUserInfo(userSignUpRequestDto.getUserName(), userType);
        Team team = Team.createTeam(userSignUpRequestDto.getTeamName(), null, null, user);
        saveTeam(team);
    }

    public void signOut(Long userId) {
        User findUser = getUserFromUserId(userId);
        deleteRefreshToken(findUser);
    }

    private void deleteRefreshToken(User user) {
        user.updateRefreshToken(null);
        refreshTokenRepository.deleteById(user.getId());
    }

    private void saveUser(User createdUser) {
        userRepository.save(createdUser);
    }

    private void updateRefreshToken(String refreshToken, User user) {
        user.updateRefreshToken(refreshToken);
        refreshTokenRepository.save(createRefreshToken(user.getId(), refreshToken));
    }

    private TokenInfo issueAccessTokenAndRefreshToken(User user) {
        return jwtProvider.issueToken(user.getId());
    }

    private User getUserFromUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private User getUserByPlatformUserInfo(PlatformUserInfo platformUserInfo, Platform platform) {
        return userRepository.findByPlatformId(platformUserInfo.getId())
                .orElse(User.createUser(platformUserInfo, platform, generateRandomUuid(platformUserInfo)));
    }

    private PlatformUserInfo getPlatformUserInfoFromRestTemplate(Platform platform, String authToken) {
        return restTemplateProvider.getUserInfoUsingRestTemplate(platform, authToken);
    }

    private void saveTeam(Team team) {
        teamRepository.save(team);
    }

    private String generateRandomUuid(PlatformUserInfo platformUserInfo) {
        UUID randomUuid = UUID.randomUUID();
        String uuidAsString = randomUuid.toString().replace("-", "");
        return platformUserInfo.getId() + "_" + uuidAsString.substring(0, 6);
    }

}
