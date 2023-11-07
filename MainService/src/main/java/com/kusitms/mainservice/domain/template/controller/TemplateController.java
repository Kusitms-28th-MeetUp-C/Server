package com.kusitms.mainservice.domain.template.controller;


import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.response.GetTeamForSaveTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.SearchTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;

    @GetMapping("/title/{title}")
    public ResponseEntity<SuccessResponse<?>> getTemplateBytitle(@PathVariable final String title){
        final SearchTemplateResponseDto searchTemplateResponseDtoList = templateService.searchTemplatesByTitle(title);
        return SuccessResponse.ok(searchTemplateResponseDtoList);
    }

    @GetMapping("/templateType/{templateType}")
    public ResponseEntity<SuccessResponse<?>> getTemplateByCategoty(@PathVariable final TemplateType templateType){
        final SearchTemplateResponseDto searchTemplateResponseDtoList = templateService.searchTemplatesByCategory(templateType);
        return SuccessResponse.ok(searchTemplateResponseDtoList);
    }

    @GetMapping("/detail")
    public ResponseEntity<SuccessResponse<?>> getTemplateDetailBytemplateId(@RequestParam Long templateId){
        final TemplateDetailResponseDto templateDetailResponseDto = templateService.getTemplateDetail(templateId);
        return SuccessResponse.ok(templateDetailResponseDto);
    }

    @GetMapping("/save")
    public ResponseEntity<SuccessResponse<?>> saveTemplate(){
        final GetTeamForSaveTemplateResponseDto getTeamForSaveTemplateResponseDto = templateService.getTeamForSaveTemplate();
        return SuccessResponse.ok(getTeamForSaveTemplateResponseDto);
    }
}
