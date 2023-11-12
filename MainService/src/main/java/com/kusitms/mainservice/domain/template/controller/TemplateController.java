package com.kusitms.mainservice.domain.template.controller;


import com.kusitms.mainservice.domain.roadmap.dto.request.SearchRoadmapRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.SearchRoadmapResponseDto;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.request.SearchTemplateRequsetDto;
import com.kusitms.mainservice.domain.template.dto.response.GetTeamForSaveTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.SaveTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.SearchTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kusitms.mainservice.domain.template.domain.TemplateType.getEnumTemplateTypeFromStringTemplateType;


@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;

//    @GetMapping("/get/page")
//    public ResponseEntity<SuccessResponse<?>> find(Pageable pageable){
//
//        return SuccessResponse.ok(templateService.getTemplate(pageable, getEnumTemplateTypeFromStringTemplateType("it")));
//    }
    @PostMapping("/get")
    public ResponseEntity<SuccessResponse<?>> getTemplateByTitleAndTemplateType(@RequestBody SearchTemplateRequsetDto searchTemplateRequsetDto,@PageableDefault(size=12) Pageable pageable){
        final SearchTemplateResponseDto searchTemplateResponseDto = templateService.searchTemplateByTitleAndRoadmapType(searchTemplateRequsetDto, pageable);
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
