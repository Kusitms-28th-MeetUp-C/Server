package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomRoadmapSpaceRepository extends JpaRepository<CustomRoadmapSpace, Long> {
    List<CustomRoadmapSpace> findAllByCustomRoadmapId(Long id);
}
