package com.kusitms.mainservice.domain.user.controller;


import com.kusitms.mainservice.domain.team.dto.request.TeamRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.TeamRoadmapRequestDto;
import com.kusitms.mainservice.domain.team.dto.request.UpdateTeamRequestDto;
import com.kusitms.mainservice.domain.user.dto.request.*;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.user.dto.response.UserAuthResponseDto;
import com.kusitms.mainservice.domain.user.service.AuthService;
import com.kusitms.mainservice.domain.team.service.TeamService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final AuthService authService;

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
}
