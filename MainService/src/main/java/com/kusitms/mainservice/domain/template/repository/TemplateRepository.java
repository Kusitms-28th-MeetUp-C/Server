package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findTop3ByTemplateType(TemplateType templateType);
    List<Template> findTop4ByTemplateType(TemplateType templateType);
    List<Template> findFirst4ByTemplateTypeAndIdNotIn(TemplateType templateType,List<Long> list);
    List<Template> findTop5ByTemplateType(TemplateType templateType);

    List<Template> findByTitleContaining(String keyword);
    Optional<Template> findById(Long Id);
    int countByUser(User user);
    int countByUserId(Long userId);
    @Query("SELECT t.user FROM Template t WHERE t.id = :template_id")
    User findUserById(@Param("template_id") Long template_id);
    @Query("SELECT t FROM Template t " +
            "WHERE (:templateType = 'ALL' OR t.templateType = :templateType) " +
            "AND t.title LIKE %:title%")
    List<Template> findByTitleAndTemplateType(
            @Param("title") String title,
            @Param("templateType") TemplateType templateType
    );

    List<Template> findByTemplateType(TemplateType templateType);
}
