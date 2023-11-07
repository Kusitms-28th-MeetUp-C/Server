package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDownloadDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.CustomTemplateService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/custom/template")
@RestController
public class CustomTemplateController {
    private final CustomTemplateService customTemplateService;

    @GetMapping("/detail/team")
    public ResponseEntity<SuccessResponse<?>> getCustomTemplateDetailInfo(@UserId final Long userId,
                                                                          @RequestParam final Long templateId,
                                                                          @RequestParam final String roadmapTitle,
                                                                          @RequestParam final String teamTitle) {
        final CustomTemplateDetailResponseDto responseDto = customTemplateService.getCustomTemplateDetailInfo(userId, roadmapTitle, teamTitle, templateId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailInfo(@RequestParam final Long templateId) {
        final TemplateDownloadDetailResponseDto responseDto = customTemplateService.getTemplateDetailInfo(templateId);
        return SuccessResponse.ok(responseDto);
    }
}
