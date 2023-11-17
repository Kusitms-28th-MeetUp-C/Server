package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.request.TemplateReviewRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateSharingRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateTeamRequestDto;
import com.kusitms.mainservice.domain.template.dto.response.CreateTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.OriginalTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDownloadDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateManageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/manage/template")
@RestController
public class TemplateManageController {
    private final TemplateManageService templateManageService;

    @PostMapping("/team")
    public ResponseEntity<SuccessResponse<?>> addTemplateToTeam(@UserId final Long userId,
                                                                @RequestBody final TemplateTeamRequestDto requestDto){
        templateManageService.addTemplateToTeam(userId, requestDto);
        return SuccessResponse.created(null);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createSharingTemplate(@UserId final Long userId,
                                                                    @RequestBody final TemplateSharingRequestDto requestDto){
        CreateTemplateResponseDto createTemplateResponseDto = templateManageService.createSharingTemplate(userId, requestDto);
        return SuccessResponse.created(createTemplateResponseDto);
    }

    @GetMapping("/team")
    public ResponseEntity<SuccessResponse<?>> getTeamTemplateDetailInfo(@UserId final Long userId,
                                                                        @RequestParam final Long templateId,
                                                                        @RequestParam final String roadmapTitle,
                                                                        @RequestParam final String teamTitle) {
        final CustomTemplateDetailResponseDto responseDto = templateManageService.getTeamTemplateDetailInfo(userId, roadmapTitle, teamTitle, templateId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailInfo(@PathVariable final Long templateId,
                                                                    @RequestParam final boolean isOpened) {
        final TemplateDownloadDetailResponseDto responseDto;
        if(isOpened) responseDto = templateManageService.getPreDownloadTemplateInfo(templateId);
        else responseDto = templateManageService.getDownloadCustomTemplateDetailInfo(templateId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/review")
    public ResponseEntity<SuccessResponse<?>> createTemplateReview(@UserId final Long userId,
                                                                   @RequestBody final TemplateReviewRequestDto requestDto) {
        templateManageService.createTemplateReview(userId, requestDto);
        return SuccessResponse.created(null);
    }
}
