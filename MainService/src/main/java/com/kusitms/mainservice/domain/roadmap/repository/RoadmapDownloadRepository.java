package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import com.kusitms.mainservice.domain.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoadmapDownloadRepository extends JpaRepository<RoadmapDownload, Long> {
    Optional<RoadmapDownload> findByRoadmapIdAndUserId(Long roadmapId, Long userId);
    @Query("SELECT COUNT(rd) FROM RoadmapDownload rd WHERE rd.roadmap = :roadmap")
    int countDownloadsByRoadmap(@Param("roadmap")Roadmap roadmap);

}
