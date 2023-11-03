package com.kusitms.mainservice.domain.user.controller;


import com.kusitms.mainservice.domain.user.dto.request.TeamRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignInRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.UserSignUpRequestDto;
import com.kusitms.mainservice.domain.user.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import com.kusitms.mainservice.domain.user.service.AuthService;
import com.kusitms.mainservice.domain.user.service.TeamService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final AuthService authService;
    private final TeamService teamService;

    @PostMapping("/signIn")
    public ResponseEntity<SuccessResponse<?>> signIn(@RequestParam final String authToken,
                                                     @RequestBody final UserSignInRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signIn(requestDto, authToken);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/signUp")
    public ResponseEntity<SuccessResponse<?>> signUp(@RequestParam final String authToken,
                                                     @RequestBody final UserSignUpRequestDto requestDto) {
        final UserAuthResponseDto responseDto = authService.signUp(requestDto, authToken);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/team")
    public ResponseEntity<SuccessResponse<?>> createTeam(@RequestHeader final Long userId,
                                                         @RequestBody final TeamRequestDto requestDto) {
        final TeamResponseDto responseDto = teamService.createTeam(userId, requestDto);
        return SuccessResponse.ok(responseDto);
    }

}
