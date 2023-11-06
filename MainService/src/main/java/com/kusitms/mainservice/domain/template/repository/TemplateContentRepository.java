package com.kusitms.mainservice.domain.template.repository;



import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TemplateContentRepository extends MongoRepository<TemplateContent, Long> {
    TemplateContent findByTemplateId(Long templateId);
}

