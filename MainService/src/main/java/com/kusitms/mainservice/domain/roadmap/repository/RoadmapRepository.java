package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.user.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<Roadmap> findTop6ByRoadmapTypeAndIdNot(RoadmapType roadmapType, Long Id);
    Page<Roadmap> findByTitleContainingAndRoadmapType(String title, RoadmapType roadmapType, Pageable pageable);

    Page<Roadmap> findByTitleContaining(String title, Pageable pageable);
    Page<Roadmap> findByRoadmapType(RoadmapType roadmapType, Pageable pageable);
    Page<Roadmap> findAll(Pageable pageable);
    List<Roadmap> findTop6ByRoadmapType(RoadmapType roadmapType);
    List<Roadmap> findFirst6ByRoadmapTypeAndIdNotIn(RoadmapType roadmapType,List<Long> list);

    int countByUser(User user);

    int countByUserId(Long userId);
}
