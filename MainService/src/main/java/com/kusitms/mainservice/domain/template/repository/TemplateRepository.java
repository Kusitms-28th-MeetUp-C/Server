package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findTop3ByTemplateType(TemplateType templateType);
    List<Template> findAllByTemplateType(TemplateType templateType);
    List<Template> findByTitleContaining(String keyword);

}
