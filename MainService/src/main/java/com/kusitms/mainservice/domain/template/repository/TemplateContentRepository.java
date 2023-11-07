package com.kusitms.mainservice.domain.template.repository;



import com.kusitms.mainservice.domain.template.domain.TemplateContent;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;


@EnableMongoRepositories
public interface TemplateContentRepository extends MongoRepository<TemplateContent, String> {
    Optional<TemplateContent> findById(String id);

//    Optional<TemplateContent> findById(ObjectId objectId);
}

