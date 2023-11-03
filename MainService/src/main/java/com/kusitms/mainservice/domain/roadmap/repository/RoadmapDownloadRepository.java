package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoadmapDownloadRepository extends JpaRepository<RoadmapDownload, Long> {
    Optional<RoadmapDownload> findByTeamId(Long teamId);
    Optional<RoadmapDownload> findByRoadmapIdAndUserId(Long roadmapId, Long userId);
}
