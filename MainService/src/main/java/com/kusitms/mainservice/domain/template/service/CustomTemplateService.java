package com.kusitms.mainservice.domain.template.service;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapSpaceDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapRepository;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamSpaceResponseDto;
import com.kusitms.mainservice.domain.team.repository.TeamRepository;
import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.dto.response.BaseCustomTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDownloadDetailResponseDto;
import com.kusitms.mainservice.domain.template.repository.CustomTemplateRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomTemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateContentRepository teamContentRepository;
    private final CustomTemplateRepository customTemplateRepository;
    private final CustomRoadmapRepository customRoadmapRepository;
    private final TeamRepository teamRepository;

    public CustomTemplateDetailResponseDto getTeamTemplateDetailInfo(Long userId, String roadmapTitle, String teamTitle, Long templateId) {
        CustomTemplate customTemplate = getCustomTemplateFromTemplateId(templateId);
        TemplateContent templateContent = getTemplateContentFromTemplateId(templateId);
        CustomRoadmap relatedRoadmap = getCustomRoadmapFromUserIdAndTitle(userId, roadmapTitle);
        CustomRoadmapDetailResponseDto baseRoadmapResponseDto = createCustomRoadmapDetailResponseDto(relatedRoadmap);
        Team team = getTeamFromTitleAndUserId(userId, teamTitle);
        TeamResponseDto teamResponseDto = createTeamResponseDto(team);
        return CustomTemplateDetailResponseDto.of(customTemplate, templateContent.getContent(), baseRoadmapResponseDto, teamResponseDto);
    }

    public TemplateDownloadDetailResponseDto getDownloadTemplateDetailInfo(Long templateId) {
        Template template = getTemplateFromTemplateId(templateId);
        TemplateContent templateContent = getTemplateContentFromTemplateId(templateId);
        return TemplateDownloadDetailResponseDto.ofTemplate(template, templateContent.getContent());
    }

    public TemplateDownloadDetailResponseDto getDownloadCustomTemplateDetailInfo(Long templateId) {
        CustomTemplate customTemplate = getCustomTemplateFromTemplateId(templateId);
        TemplateContent templateContent = getTemplateContentFromTemplateId(templateId);
        return TemplateDownloadDetailResponseDto.ofCustomTemplate(customTemplate, templateContent.getContent());
    }

    private TeamResponseDto createTeamResponseDto(Team team) {
        List<TeamSpaceResponseDto> teamSpaceResponseDtoList = TeamSpaceResponseDto.listOf(team.getTeamSpaceList());
        return TeamResponseDto.of(team, teamSpaceResponseDtoList);
    }

    private CustomRoadmapDetailResponseDto createCustomRoadmapDetailResponseDto(CustomRoadmap relatedRoadmap) {
        List<CustomRoadmapSpaceDetailResponseDto> customRoadmapSpaceDetailList
                = createTeamRoadmapSpaceDetailResponseDto(relatedRoadmap.getCustomRoadmapSpaceList());
        return CustomRoadmapDetailResponseDto.of(relatedRoadmap, customRoadmapSpaceDetailList);
    }

    private List<CustomRoadmapSpaceDetailResponseDto> createTeamRoadmapSpaceDetailResponseDto(List<CustomRoadmapSpace> teamRoadmapSpaceList) {
        return teamRoadmapSpaceList.stream()
                .map(customRoadmapSpace ->
                        CustomRoadmapSpaceDetailResponseDto.of(customRoadmapSpace, BaseCustomTemplateResponseDto.listOf(customRoadmapSpace)))
                .collect(Collectors.toList());
    }

    private Team getTeamFromTitleAndUserId(Long userId, String title) {
        return teamRepository.findByUserIdAndTitle(userId, title)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    private CustomRoadmap getCustomRoadmapFromUserIdAndTitle(Long userId, String title) {
        return customRoadmapRepository.findByUserIdAndTitle(userId, title)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_NOT_FOUND));
    }

    private CustomTemplate getCustomTemplateFromTemplateId(Long templateId) {
        return customTemplateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private TemplateContent getTemplateContentFromTemplateId(Long templateId) {
        return teamContentRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_CONTENT_NOT_FOUND));
    }

    private Template getTemplateFromTemplateId(Long templateId) {
        return templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }
}
