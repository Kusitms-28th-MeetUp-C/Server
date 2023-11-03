package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateDownloadRepository extends JpaRepository<TemplateDownload, Long> {
    List<TemplateDownload> findAllByUserId(Long userId);
}
