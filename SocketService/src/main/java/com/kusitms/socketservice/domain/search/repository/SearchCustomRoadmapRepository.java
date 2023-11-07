package com.kusitms.socketservice.domain.search.repository;

import com.kusitms.socketservice.domain.search.domain.RoadmapType;
import com.kusitms.socketservice.domain.search.domain.SearchCustomRoadmap;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchCustomRoadmapRepository extends MongoRepository<SearchCustomRoadmap, String> {
    List<SearchCustomRoadmap> findByUserIdAndRoadmapTypeAndTitleContainingIgnoreCase(Long userId, RoadmapType roadmapType, String keyword);

    List<SearchCustomRoadmap> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);
}
