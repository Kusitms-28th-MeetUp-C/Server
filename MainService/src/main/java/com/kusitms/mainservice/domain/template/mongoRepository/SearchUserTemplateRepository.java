package com.kusitms.mainservice.domain.template.mongoRepository;

import com.kusitms.mainservice.domain.template.domain.SearchUserTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchUserTemplateRepository extends MongoRepository<SearchUserTemplate, String> {
}
