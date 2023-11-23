package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
    List<Roadmap> findTop6ByRoadmapTypeAndIdNot(RoadmapType roadmapType, Long Id);

    Page<Roadmap> findByTitleContainingAndRoadmapTypeOrderByCreateDateDesc(String title, RoadmapType roadmapType, Pageable pageable);

    Page<Roadmap> findByTitleContainingOrderByCreateDateDesc(String title, Pageable pageable);

    Page<Roadmap> findByRoadmapTypeOrderByCreateDateDesc(RoadmapType roadmapType, Pageable pageable);

    Page<Roadmap> findAllByOrderByCreateDateDesc(Pageable pageable);

    int countByUser(User user);

    Page<Roadmap> findAllByUserId(Long id, Pageable pageable);

    List<Roadmap> findAllByUserId(Long id);

}
