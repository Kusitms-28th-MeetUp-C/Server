package com.kusitms.mainservice.domain.template.controller;

<<<<<<< HEAD
import com.kusitms.mainservice.domain.template.domain.TemplateData;
import com.kusitms.mainservice.domain.template.service.TemplateService;
import com.kusitms.mainservice.domain.template.dto.response.OriginalTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.UserBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.service.TemplateUserService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import com.kusitms.mainservice.global.config.auth.UserId;
=======
import com.kusitms.mainservice.domain.template.service.TemplateService;
>>>>>>> 661523a1ea16d4065c3e905e784ada6d1cb00099
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/template")
@RestController
public class TemplateController {
    private final TemplateService templateService;
<<<<<<< HEAD
    private final TemplateUserService templateUserService;

=======
//    @GetMapping
//    public ResponseEntity<SuccessResponse<?>> getPlatform(){
//        final TemplateResponseDto responseDto = templateService;
//        return SuccessResponse.ok(responseDto);
//    }
>>>>>>> 661523a1ea16d4065c3e905e784ada6d1cb00099

}
