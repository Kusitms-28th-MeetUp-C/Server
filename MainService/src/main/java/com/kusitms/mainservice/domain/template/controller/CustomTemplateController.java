package com.kusitms.mainservice.domain.template.controller;

import com.kusitms.mainservice.domain.template.service.CustomTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/template/roadmap")
@RestController
public class CustomTemplateController {
    private final CustomTemplateService customTemplateService;
}
