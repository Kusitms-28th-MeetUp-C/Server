package com.kusitms.mainservice.domain.roadmap.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDetail;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.dto.response.BaseRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapDownloadRepository;
import com.kusitms.mainservice.domain.template.dto.response.TemplateTitleResponseDto;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.global.error.ErrorCode.ROADMAP_DOWNLOAD_NOT_FOUND;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoadmapUserService {
    private final RoadmapDownloadRepository roadmapDownloadRepository;

    public BaseRoadmapResponseDto getTeamRoadmap(Long teamId) {
        RoadmapDownload downloadRoadmap = getDownloadRoadmapFromTeamId(teamId);
        Roadmap roadmap = downloadRoadmap.getRoadmap();
        List<RoadmapDetailResponseDto> roadmapDetailResponseDtoList = createRoadmapDetailResponseDtoList(roadmap);
        return BaseRoadmapResponseDto.of(roadmap, roadmapDetailResponseDtoList);
    }

    private List<RoadmapDetailResponseDto> createRoadmapDetailResponseDtoList(Roadmap roadmap) {
        List<RoadmapDetail> roadmapDetailList = roadmap.getRoadmapDetailList();
        return roadmapDetailList.stream()
                .map(roadmapDetail ->
                        RoadmapDetailResponseDto.of(
                                roadmapDetail,
                                createTemplateTitleResponseDtoList(roadmapDetail)))
                .collect(Collectors.toList());
    }

    private List<TemplateTitleResponseDto> createTemplateTitleResponseDtoList(RoadmapDetail roadmapDetail) {
        List<RoadmapTemplate> roadmapTemplateList = roadmapDetail.getRoadmapTemplateList();
        return roadmapTemplateList.stream()
                .map(roadmapTemplate -> TemplateTitleResponseDto.of(roadmapTemplate.getTemplateDownload().getTemplate()))
                .collect(Collectors.toList());
    }

    private RoadmapDownload getDownloadRoadmapFromTeamId(Long teamId) {
        return roadmapDownloadRepository.findByTeamId(teamId)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_DOWNLOAD_NOT_FOUND));
    }


}
