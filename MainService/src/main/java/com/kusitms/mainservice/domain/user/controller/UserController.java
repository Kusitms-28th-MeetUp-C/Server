package com.kusitms.mainservice.domain.user.controller;


import com.kusitms.mainservice.domain.user.dto.request.UserSignInRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignUpRequestDto;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import com.kusitms.mainservice.domain.user.service.AuthService;
import com.kusitms.mainservice.domain.user.service.UserService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final AuthService authService;

    @PostMapping("/signIn")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestHeader("Authorization") final String authToken,
                                                     @RequestBody final UserSignInRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signIn(requestDto, authToken);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<?>> signUp(@UserId final Long userId,
                                                     @RequestBody final UserSignUpRequestDto requestDto) {
        authService.signUp(userId, requestDto);
        return SuccessResponse.ok(null);
    }

    @PatchMapping("/signout")
    public ResponseEntity<SuccessResponse<?>> signOut(@UserId final Long userId) {
        authService.signOut(userId);
        return SuccessResponse.ok(null);
    }
}
