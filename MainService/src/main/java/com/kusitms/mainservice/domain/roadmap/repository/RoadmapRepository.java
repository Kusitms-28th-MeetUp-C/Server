package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.user.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    @Query("SELECT r FROM Roadmap r " +
            "LEFT JOIN FETCH r.user u " +
            "LEFT JOIN FETCH r.roadmapSpaceList rs " +
            "LEFT JOIN FETCH rs.RoadmapTemplateList rt " +
            "LEFT JOIN FETCH rt.template t " +
            "WHERE u.id = :makerId AND t.id = :templateId")
    Optional<Roadmap> findRoadmapByMakerIdAndTemplateId(@Param("makerId") Long makerId, @Param("templateId") Long templateId);

    @Query("SELECT r FROM Roadmap r " +
            "WHERE r.title = :title AND r.roadmapType = :roadmapType")
    List<Roadmap> findByTitleAndRoadmapType(
            @Param("title") String title,
            @Param("roadmapType") RoadmapType roadmapType
    );

    List<Roadmap> findByTitleContaining(String title);
    List<Roadmap> findByRoadmapType(RoadmapType roadmapType);
    List<Roadmap> findTop3ByRoadmapType(RoadmapType roadmapType);

    int countByUser(User user);

    int countByUserId(Long userId);
}
