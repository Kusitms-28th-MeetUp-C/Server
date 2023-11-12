package com.kusitms.mainservice.domain.template.mongoRepository;

import com.kusitms.mainservice.domain.template.domain.CustomTemplateContent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomTemplateContentRepository extends MongoRepository<CustomTemplateContent, String> {
    Optional<CustomTemplateContent> findByTemplateId(Long templateId);
}
