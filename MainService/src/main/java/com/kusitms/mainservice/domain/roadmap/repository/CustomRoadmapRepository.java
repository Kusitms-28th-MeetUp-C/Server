package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRoadmapRepository extends JpaRepository<CustomRoadmap, Long> {
}
