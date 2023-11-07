package com.kusitms.socketservice.domain.search.repository;

import com.kusitms.socketservice.domain.search.domain.SearchCustomTemplate;
import com.kusitms.socketservice.domain.search.domain.TemplateType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchCustomTemplateRepository extends MongoRepository<SearchCustomTemplate, String> {
    List<SearchCustomTemplate> findByUserIdAndTemplateTypeAndTitleContainingIgnoreCase(Long userId, TemplateType templateType, String keyword);

    List<SearchCustomTemplate> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);
}
