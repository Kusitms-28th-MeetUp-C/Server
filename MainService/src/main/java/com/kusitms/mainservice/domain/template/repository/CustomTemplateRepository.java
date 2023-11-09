package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomTemplateRepository extends JpaRepository<CustomTemplate,Long> {

}
