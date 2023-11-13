package com.kusitms.mainservice.domain.article.repository;

import com.kusitms.mainservice.domain.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
