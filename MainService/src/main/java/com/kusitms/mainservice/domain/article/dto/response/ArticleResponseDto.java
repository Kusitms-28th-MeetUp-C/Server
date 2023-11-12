package com.kusitms.mainservice.domain.article.dto.response;

import com.kusitms.mainservice.domain.article.domain.Article;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String image;
    public static ArticleResponseDto of(Article article){
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .image(article.getImage())
                .build();
    }
}
