package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

// MySQL Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    // MySQL 연산을 추가할 수 있음
}
