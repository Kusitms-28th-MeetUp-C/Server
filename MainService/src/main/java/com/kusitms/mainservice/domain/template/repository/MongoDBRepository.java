package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.MongoDBTemplate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MongoDBRepository extends JpaRepository<MongoDBTemplate, String> {

}

