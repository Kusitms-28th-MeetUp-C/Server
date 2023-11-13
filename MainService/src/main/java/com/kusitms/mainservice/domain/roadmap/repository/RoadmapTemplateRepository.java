package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadmapTemplateRepository extends JpaRepository<RoadmapTemplate, Long> {
}
