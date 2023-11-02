package com.kusitms.mainservice.domain.roadmap.repository;

import com.kusitms.mainservice.domain.roadmap.domain.UserRoadmap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoadmapRepository extends JpaRepository<UserRoadmap, Long> {
    List<UserRoadmap> findAllByUserId(Long userId);
}
