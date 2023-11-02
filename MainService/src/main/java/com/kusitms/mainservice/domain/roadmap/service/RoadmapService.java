package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDetail;
import com.kusitms.mainservice.domain.roadmap.domain.UserRoadmap;
import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.UserRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.UserRoadmapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadmapService {
    private final UserRoadmapRepository userRoadmapRepository;
    private final RoadmapRepository roadmapRepository;

    public UserRoadmapResponseDto getUserRoadmapList(Long userId) {
        List<UserRoadmap> userRoadmapList = getUserRoadmapListFromUserId(userId);
        List<BaseRoadmapResponseDto> baseRoadmapResponseDtoList = createBaseRoadmapResponseDtoList(userRoadmapList);
        return UserRoadmapResponseDto.of(baseRoadmapResponseDtoList);
    }

    private List<BaseRoadmapResponseDto> createBaseRoadmapResponseDtoList(List<UserRoadmap> userRoadmapList) {
        List<Roadmap> roadmapList = getRoadmapListFromUserRoadmapList(userRoadmapList);
        return roadmapList.stream()
                .map(roadmap -> BaseRoadmapResponseDto.of(roadmap, createRoadmapDetailRespoonseDtoList(roadmap)))
                .collect(Collectors.toList());
    }

    private List<Roadmap> getRoadmapListFromUserRoadmapList(List<UserRoadmap> userRoadmapList) {
        return userRoadmapList.stream()
                .map(UserRoadmap::getRoadmap)
                .collect(Collectors.toList());
    }

    private List<RoadmapDetailResponseDto> createRoadmapDetailRespoonseDtoList(Roadmap roadmap) {
        List<RoadmapDetail> roadmapDetailList = roadmap.getRoadmapDetailList();
        return roadmapDetailList.stream()
                .map(RoadmapDetailResponseDto::of)
                .collect(Collectors.toList());
    }

    private List<UserRoadmap> getUserRoadmapListFromUserId(Long userId) {
        return userRoadmapRepository.findAllByUserId(userId);
    }


}
