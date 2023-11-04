package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;
//    @GetMapping
//    public ResponseEntity<SuccessResponse<?>> getPlatform(){
//        final TemplateResponseDto responseDto = templateService;
//        return SuccessResponse.ok(responseDto);
//    }

}
