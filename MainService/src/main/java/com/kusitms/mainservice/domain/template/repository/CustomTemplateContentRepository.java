package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.CustomTemplateContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface CustomTemplateContentRepository extends MongoRepository<CustomTemplateContent, String> {
    Optional<CustomTemplateContent> findByTemplateId(String id);
}
