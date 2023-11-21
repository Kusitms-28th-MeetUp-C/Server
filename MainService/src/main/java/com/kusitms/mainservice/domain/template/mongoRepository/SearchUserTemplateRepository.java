package com.kusitms.mainservice.domain.template.mongoRepository;

import com.kusitms.mainservice.domain.template.domain.SearchUserTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SearchUserTemplateRepository extends MongoRepository<SearchUserTemplate, String> {
    Optional<SearchUserTemplate> findByTemplateId(Long templateId);
}
