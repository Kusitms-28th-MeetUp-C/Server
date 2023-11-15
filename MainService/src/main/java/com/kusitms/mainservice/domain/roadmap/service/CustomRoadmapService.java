package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.*;
import com.kusitms.mainservice.domain.roadmap.dto.request.RoadmapSharingRequestDto;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapSpaceRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.request.TemplateSharingRequestDto;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.kusitms.mainservice.domain.roadmap.domain.RoadmapType.getEnumRoadmapTypeFromStringRoadmapType;
import static com.kusitms.mainservice.domain.template.domain.TemplateContent.createTemplateContent;
import static com.kusitms.mainservice.domain.template.domain.TemplateType.getEnumTemplateTypeFromStringTemplateType;
import static com.kusitms.mainservice.global.error.ErrorCode.ROADMAP_NOT_FOUND;
import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;


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
