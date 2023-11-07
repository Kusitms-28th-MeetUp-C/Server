package com.kusitms.socketservice.domain.search.repository;

import com.kusitms.socketservice.domain.search.domain.RoadmapType;
import com.kusitms.socketservice.domain.search.domain.SearchCustomStep;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SearchCustomStepRepository extends MongoRepository<SearchCustomStep, String> {
    List<SearchCustomStep> findByUserIdAndRoadmapTypeAndTitleContainingIgnoreCase(Long userId, RoadmapType roadmapType, String keyword);

    List<SearchCustomStep> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);
}
