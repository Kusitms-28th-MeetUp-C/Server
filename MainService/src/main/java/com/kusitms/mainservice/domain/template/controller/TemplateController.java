package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.request.SearchTemplateRequsetDto;
import com.kusitms.mainservice.domain.template.dto.response.SearchBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateManageService;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;
    private final TemplateManageService templateManageService;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> getTemplateByTitleAndTemplateType(@RequestBody SearchTemplateRequsetDto searchTemplateRequsetDto, @PageableDefault(size = 12) Pageable pageable) {
        final Page<SearchBaseTemplateResponseDto> searchTemplateResponseDto = templateService.searchTemplateByTitleAndRoadmapType(searchTemplateRequsetDto, pageable);
        return SuccessResponse.ok(searchTemplateResponseDto);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailBytemplateId(@RequestParam Long templateId) {
        final TemplateDetailResponseDto templateDetailResponseDto = templateService.getTemplateDetail(templateId);
        return SuccessResponse.ok(templateDetailResponseDto);
    }

}
