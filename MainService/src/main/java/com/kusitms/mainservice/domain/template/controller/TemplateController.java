package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.response.OriginalTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.UserBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.domain.template.service.TemplateUserService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateUserService templateUserService;
//    @GetMapping
//    public ResponseEntity<SuccessResponse<?>> getPlatform(){
//        final TemplateResponseDto responseDto = templateService;
//        return SuccessResponse.ok(responseDto);
//    }

    @GetMapping("/user")
    public ResponseEntity<SuccessResponse<?>> getTemplateListOfUser(@UserId final Long userId) {
        final UserBaseTemplateResponseDto responseDto = templateUserService.getTemplateListOfUser(userId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/original")
    public ResponseEntity<SuccessResponse<?>> getOriginalTemplateOfUser(@RequestParam final Long templateId) {
        final OriginalTemplateDetailResponseDto responseDto = templateUserService.getOriginalTemplateOfUser(templateId);
        return SuccessResponse.ok(responseDto);
    }

}
