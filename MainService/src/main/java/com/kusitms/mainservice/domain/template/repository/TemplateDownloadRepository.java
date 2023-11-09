package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TemplateDownloadRepository extends JpaRepository<TemplateDownload, Long> {
    Optional<TemplateDownload> findByUserIdAndTemplateId(Long userId, Long templateId);

    @Query("SELECT COUNT(td) FROM TemplateDownload td WHERE td.template = :template")
    int countDownloadsByTemplate(@Param("template") Optional<Template> template);
}
