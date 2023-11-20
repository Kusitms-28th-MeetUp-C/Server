package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.mypage.domain.SharedType;
import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
    List<Template> findTop4ByTemplateTypeAndIdNot(TemplateType templateType, Long Id);
    Page<Template> findAllByOrderByCreateDateDesc(Pageable pageable);
    Page<Template> findByTitleContainingOrderByCreateDateDesc(String keyword,Pageable pageable);
    Optional<Template> findById(Long Id);
    int countByUser(User user);

    Page<Template> findByTitleContainingAndTemplateTypeOrderByCreateDateDesc(String title, TemplateType templateType,Pageable pageable);

    Page<Template> findByTemplateTypeOrderByCreateDateDesc(TemplateType templateType, Pageable pageable);
    Page<Template> findAllByUserId(Long userId, Pageable pageable);
    List<Template> findAllByUserId(Long userId);
}
