package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findTop3ByTemplateType(TemplateType templateType);
    List<Template> findAllByTemplateType(TemplateType templateType);
    List<Template> findByTitleContaining(String keyword);
    Template findByTemplateId(Long templateId);
    int countByUser(User user);
    @Query("SELECT t.user FROM Template t WHERE t.id = :templateId")
    User findUserByTemplateId(@Param("templateId") Long templateId);
}
