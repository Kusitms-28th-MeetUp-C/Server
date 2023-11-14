package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapSpaceRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.kusitms.mainservice.global.error.ErrorCode.ROADMAP_NOT_FOUND;


@RequiredArgsConstructor
@Transactional
@Service
public class CustomRoadmapService {
    private final CustomRoadmapSpaceRepository customRoadmapSpaceRepository;

    public void updateCompletedRoadmapSpace(Long stepId, boolean isCompleted) {
        CustomRoadmapSpace customRoadmapSpace = getCustomRoadmapSpaceFromStepId(stepId);
        customRoadmapSpace.updateCompletedAneEndTime(isCompleted);
        updateCustomRoadmapEndTime(customRoadmapSpace);
    }

    private void updateCustomRoadmapEndTime(CustomRoadmapSpace customRoadmapSpace) {
        CustomRoadmap customRoadmap = customRoadmapSpace.getCustomRoadmap();
        boolean isCompleted = getCompleteStateFromCustomRoadmap(customRoadmap);
        if(!isCompleted) customRoadmap.updateEndDate(LocalDate.now());
    }

    private boolean getCompleteStateFromCustomRoadmap(CustomRoadmap customRoadmap){
        return customRoadmap.getCustomRoadmapSpaceList().stream()
                .anyMatch(currentRoadmapSpace -> !currentRoadmapSpace.isCompleted());
    }

    private CustomRoadmapSpace getCustomRoadmapSpaceFromStepId(Long steId) {
        return customRoadmapSpaceRepository.findById(steId)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_NOT_FOUND));
    }
}
