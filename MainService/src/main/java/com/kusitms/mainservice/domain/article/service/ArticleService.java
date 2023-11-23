package com.kusitms.mainservice.domain.article.service;

import com.kusitms.mainservice.domain.article.domain.Article;
import com.kusitms.mainservice.domain.article.dto.response.ArticleResponseDto;
import com.kusitms.mainservice.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<ArticleResponseDto> getArticleList() {
        return createArticleResponseDtoList();
    }

    private List<ArticleResponseDto> createArticleResponseDtoList() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream()
                .map(ArticleResponseDto::of)
                .collect(Collectors.toList());
    }
}
