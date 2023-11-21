package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.request.TemplateReviewRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateSharingRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateTeamRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.UpdateTemplateRequestDto;
import com.kusitms.mainservice.domain.template.dto.response.CreateTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
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

    @GetMapping("/save/user")
    public ResponseEntity<SuccessResponse<?>> saveTemplateForUser(@UserId final Long userId, @RequestParam Long templateId) {
        templateManageService.saveTemplateByUserId(userId, templateId);
        return SuccessResponse.ok(null);
    }

    @PostMapping("/update")
    public ResponseEntity<SuccessResponse<?>> updateTemplate(@UserId final Long userId, @RequestBody final UpdateTemplateRequestDto updateTemplateRequestDto) {
        templateManageService.updateTemplate(updateTemplateRequestDto);
        return SuccessResponse.ok(null);
    }

    @PostMapping("/team")
    public ResponseEntity<SuccessResponse<?>> addTemplateToTeam(@UserId final Long userId,
                                                                @RequestBody final TemplateTeamRequestDto requestDto) {
        templateManageService.addTemplateToTeam(userId, requestDto);
        return SuccessResponse.created(null);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createSharingTemplate(@UserId final Long userId,
                                                                    @RequestBody final TemplateSharingRequestDto requestDto) {
        CreateTemplateResponseDto createTemplateResponseDto = templateManageService.createSharingTemplate(userId, requestDto);
        return SuccessResponse.created(createTemplateResponseDto);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<?>> deleteTemplate(@RequestParam Long templateId) {
        templateManageService.deleteTemplateByTemplateId(templateId);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/team")
    public ResponseEntity<SuccessResponse<?>> getTeamTemplateDetailInfo(@UserId final Long userId,
                                                                        @RequestParam final Long templateId,
                                                                        @RequestParam final Long roadmapId,
                                                                        @RequestParam final String teamTitle) {
        final CustomTemplateDetailResponseDto responseDto = templateManageService.getTeamTemplateDetailInfo(userId, roadmapId, teamTitle, templateId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailInfo(@PathVariable final Long templateId) {
        final TemplateDownloadDetailResponseDto responseDto = templateManageService.getDownloadCustomTemplateDetailInfo(templateId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/review")
    public ResponseEntity<SuccessResponse<?>> createTemplateReview(@UserId final Long userId,
                                                                   @RequestBody final TemplateReviewRequestDto requestDto) {
        templateManageService.createTemplateReview(userId, requestDto);
        return SuccessResponse.created(null);
    }
}
