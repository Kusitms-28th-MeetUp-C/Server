package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.OriginalTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDownloadDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateManageService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/template/manage")
@RestController
public class TemplateManageController {
    private final TemplateManageService templateManageService;

    @GetMapping("/team")
    public ResponseEntity<SuccessResponse<?>> getTeamTemplateDetailInfo(@UserId final Long userId,
                                                                          @RequestParam final Long templateId,
                                                                          @RequestParam final String roadmapTitle,
                                                                          @RequestParam final String teamTitle) {
        final CustomTemplateDetailResponseDto responseDto = templateManageService.getTeamTemplateDetailInfo(userId, roadmapTitle, teamTitle, templateId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailInfo(@UserId final Long userId,
                                                                    @RequestParam final Long templateId,
                                                                    @RequestParam final boolean isOpened) {
        final TemplateDownloadDetailResponseDto responseDto;
        if(isOpened) responseDto = templateManageService.getDownloadTemplateDetailInfo(userId, templateId);
        else responseDto = templateManageService.getDownloadCustomTemplateDetailInfo(templateId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/original")
    public ResponseEntity<SuccessResponse<?>> getOriginalTemplateInfo(@UserId final Long userId,
                                                                      @RequestParam final Long relatedTemplateId) {
        final OriginalTemplateResponseDto responseDto = templateManageService.getOriginalTemplateInfo(userId, relatedTemplateId);
        return SuccessResponse.ok(responseDto);
    }
}
