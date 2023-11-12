package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.dto.request.SearchTemplateRequsetDto;
import com.kusitms.mainservice.domain.template.dto.response.*;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.global.common.SuccessResponse;
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

    @PostMapping("/get")
    public ResponseEntity<SuccessResponse<?>> getTemplateByTitleAndTemplateType(@RequestBody SearchTemplateRequsetDto searchTemplateRequsetDto,@PageableDefault(size=12) Pageable pageable){
        final Page<SearchBaseTemplateResponseDto> searchTemplateResponseDto = templateService.searchTemplateByTitleAndRoadmapType(searchTemplateRequsetDto, pageable);
        return SuccessResponse.ok(searchTemplateResponseDto);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailBytemplateId(@RequestParam Long templateId){
        final TemplateDetailResponseDto templateDetailResponseDto = templateService.getTemplateDetail(templateId);
        return SuccessResponse.ok(templateDetailResponseDto);
    }
    @PostMapping("/save/user")
    public ResponseEntity<SuccessResponse<?>>saveTemplateForUser(@RequestBody SaveTemplateResponseDto saveTemplateResponseDto){
        final String dd = templateService.saveTemplateByUserId(saveTemplateResponseDto);
    return SuccessResponse.ok(dd);
    }
}
