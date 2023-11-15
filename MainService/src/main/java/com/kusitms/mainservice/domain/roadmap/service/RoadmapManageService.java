package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.roadmap.dto.request.RoadmapSharingRequestDto;
import com.kusitms.mainservice.domain.roadmap.dto.request.StepDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapSpaceRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms.mainservice.domain.roadmap.domain.RoadmapType.getEnumRoadmapTypeFromStringRoadmapType;
import static com.kusitms.mainservice.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Transactional
@Service
public class RoadmapManageService {
    private final UserRepository userRepository;
    private final RoadmapRepository roadmapRepository;
    private final RoadmapSpaceRepository roadmapSpaceRepository;
    public void createSharingRoadmap(Long userId, RoadmapSharingRequestDto roadmapSharingRequestDto) {
        User user = getUserFromUserId(userId);
        RoadmapType roadmapType = getEnumRoadmapTypeFromStringRoadmapType(roadmapSharingRequestDto.getRoadmapType());
        Roadmap createdRoadmap = Roadmap.createRoadmap(roadmapSharingRequestDto, roadmapType, user);
        saveRoadmap(createdRoadmap);
        for (int i = 0; i < roadmapSharingRequestDto.getSteps().size(); i++) {
            StepDto stepDto = roadmapSharingRequestDto.getSteps().get(i);
            RoadmapSpace createdRoadmapSpace = RoadmapSpace.createRoadmapSpace(stepDto, createdRoadmap, i);
            saveRoadmapSpace(createdRoadmapSpace);
        }

    }
    private void saveRoadmap(Roadmap createdRoadmap){
        roadmapRepository.save(createdRoadmap);
//        roadmapSpaceRepository.save(createdRoadmapSpace);
    }
    private void saveRoadmapSpace(RoadmapSpace createdRoadmapSpace){
        roadmapSpaceRepository.save(createdRoadmapSpace);
    }
    private User getUserFromUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
