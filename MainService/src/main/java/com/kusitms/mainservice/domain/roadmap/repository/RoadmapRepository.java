package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<Roadmap, Long> {
}
