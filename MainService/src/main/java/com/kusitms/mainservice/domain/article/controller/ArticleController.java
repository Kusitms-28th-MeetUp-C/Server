package com.kusitms.mainservice.domain.article.controller;

import com.kusitms.mainservice.domain.article.dto.response.ArticleResponseDto;
import com.kusitms.mainservice.domain.article.service.ArticleService;
import com.kusitms.mainservice.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/article")
@RestController
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/get")
    public ResponseEntity<SuccessResponse<?>> getAritcleList(){
        final List<ArticleResponseDto> articleResponseDtoList = articleService.getArticleList();
        return SuccessResponse.ok(articleResponseDtoList);
    }

}
