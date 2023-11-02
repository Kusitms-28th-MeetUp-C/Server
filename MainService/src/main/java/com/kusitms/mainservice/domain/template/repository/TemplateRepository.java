package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {

}
