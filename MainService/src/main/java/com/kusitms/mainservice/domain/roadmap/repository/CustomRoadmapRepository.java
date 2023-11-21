package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomRoadmapRepository extends JpaRepository<CustomRoadmap, Long> {
    @Query("SELECT cr FROM CustomRoadmap cr " +
            "JOIN FETCH cr.roadmapDownload rd " +
            "JOIN FETCH rd.user u " +
            "WHERE u.id = :userId AND cr.title = :title")
    Optional<CustomRoadmap> findByUserIdAndTitle(@Param("userId") Long userId, @Param("title") String title);
}
