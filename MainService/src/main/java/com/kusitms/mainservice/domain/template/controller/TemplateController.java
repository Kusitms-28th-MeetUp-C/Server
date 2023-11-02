//package com.kusitms.mainservice.domain.template.controller;
//
//import com.kusitms.mainservice.domain.template.dto.TemplateResponseDto;
//import com.kusitms.mainservice.domain.template.service.TemplateService;
//import com.kusitms.mainservice.global.common.SuccessResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/api/template")
//public class TemplateController {
//    private final TemplateService templateService;
//    @GetMapping
//    public ResponseEntity<SuccessResponse<?>> getPlatform(){
//        final TemplateResponseDto responseDto = templateService;
//        return SuccessResponse.ok(responseDto);
//    }
//
//}
