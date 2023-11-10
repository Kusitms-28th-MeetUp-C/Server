package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapSpaceRepository extends JpaRepository<RoadmapSpace, Long> {
    List<RoadmapSpace> findByRoadmapId(Long id);
}
