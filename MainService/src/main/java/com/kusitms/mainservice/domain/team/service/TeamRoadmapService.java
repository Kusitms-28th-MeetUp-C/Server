package com.kusitms.mainservice.domain.team.service;

import com.kusitms.mainservice.domain.roadmap.domain.*;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapSpaceDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.*;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.dto.request.TeamRoadmapRequestDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamSpaceResponseDto;
import com.kusitms.mainservice.domain.team.repository.TeamRepository;
import com.kusitms.mainservice.domain.template.domain.*;
import com.kusitms.mainservice.domain.template.dto.response.BaseCustomTemplateResponseDto;
import com.kusitms.mainservice.domain.template.mongoRepository.CustomTemplateContentRepository;
import com.kusitms.mainservice.domain.template.mongoRepository.SearchUserTemplateRepository;
import com.kusitms.mainservice.domain.template.mongoRepository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.CustomTemplateRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
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
@Transactional
@Service
public class TeamRoadmapService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final RoadmapDownloadRepository roadmapDownloadRepository;
    private final CustomRoadmapSpaceRepository customRoadmapSpaceRepository;
    private final CustomRoadmapRepository customRoadmapRepository;
    private final RoadmapRepository roadmapRepository;
    private final TemplateDownloadRepository templateDownloadRepository;
    private final TemplateContentRepository templateContentRepository;
    private final CustomTemplateRepository customTemplateRepository;
    private final CustomRoadmapTemplateRepository customRoadmapTemplateRepository;
    private final CustomTemplateContentRepository customTemplateContentRepository;
    private final SearchUserTemplateRepository searchUserTemplateRepository;

    public TeamRoadmapDetailResponseDto getTeamRoadmapDetail(Long teamId) {
        Team team = getTeamFromTeamId(teamId);
        List<TeamSpaceResponseDto> teamSpaceResponseDtoList = TeamSpaceResponseDto.listOf(team.getTeamSpaceList());
        CustomRoadmap teamRoadmap = team.getRoadmapDownload().getCustomRoadmap();
        List<CustomRoadmapSpaceDetailResponseDto> teamRoadmapSpaceDetailResponseDtoList
                = createTeamRoadmapSpaceDetailResponseDto(teamRoadmap.getCustomRoadmapSpaceList());
        Long processingNum = getProcessingNum(teamRoadmap);
        CustomRoadmapDetailResponseDto teamRoadmapDetailResponseDto
                = CustomRoadmapDetailResponseDto.of(teamRoadmap, processingNum.intValue(), teamRoadmapSpaceDetailResponseDtoList);
        return TeamRoadmapDetailResponseDto.of(team, teamSpaceResponseDtoList, teamRoadmapDetailResponseDto);
    }

    public void addTeamRoadmap(Long userId, TeamRoadmapRequestDto teamRoadmapRequestDto) {
        User user = getUserFromUserId(userId);
        Team team = getTeamFromTeamId(teamRoadmapRequestDto.getTeamId());
        Roadmap roadmap = getRoadmapFromRoadmapId(teamRoadmapRequestDto.getRoadmapId());
        RoadmapDownload roadmapDownload = createRoadmapDownload(user, roadmap, team);
        CustomRoadmap createCustomRoadmap = createCustomRoadmap(roadmap, roadmapDownload);
        List<CustomRoadmapSpace> createCustomRoadmapSpace = createCustomRoadmapSpaceList(roadmap, createCustomRoadmap, user, team);
        saveCustomRoadmapSpaceList(createCustomRoadmapSpace);
    }

    private RoadmapDownload createRoadmapDownload(User user, Roadmap roadmap, Team team) {
        RoadmapDownload roadmapDownload = RoadmapDownload.creatRoadmapDownload(user, roadmap);
        team.addRoadmapDownload(roadmapDownload);
        saveRoadmapDownload(roadmapDownload);
        return roadmapDownload;
    }

    private CustomRoadmap createCustomRoadmap(Roadmap roadmap, RoadmapDownload roadmapDownload) {
        CustomRoadmap createCustomRoadmap = CustomRoadmap.createCustomRoadmap(roadmap, roadmapDownload);
        saveCustomRoadmap(createCustomRoadmap);
        return createCustomRoadmap;
    }

    private List<CustomRoadmapSpace> createCustomRoadmapSpaceList(Roadmap roadmap, CustomRoadmap customRoadmap, User user, Team team) {
        List<RoadmapSpace> roadmapSpaceList = roadmap.getRoadmapSpaceList();
        return roadmapSpaceList.stream()
                .map(roadmapSpace -> createCustomRoadmapSpace(roadmapSpace, customRoadmap, user, team))
                .collect(Collectors.toList());
    }

    private CustomRoadmapSpace createCustomRoadmapSpace(RoadmapSpace roadmapSpace, CustomRoadmap customRoadmap, User user, Team team) {
        CustomRoadmapSpace customRoadmapSpace = CustomRoadmapSpace.createCustomRoadmapSpace(roadmapSpace, customRoadmap);
        List<Template> templateList = getTemplateListFromRoadmapTemplate(roadmapSpace.getRoadmapTemplateList());
        List<CustomTemplate> customTemplateList = createCustomTemplateList(templateList, user, team);
        List<CustomRoadmapTemplate> customRoadmapTemplate = createCustomRoadmapTemplateList(customTemplateList, customRoadmapSpace);
        saveCustomRoadmapTemplateList(customRoadmapTemplate);
        return customRoadmapSpace;
    }

    private Long getProcessingNum(CustomRoadmap relatedRoadmap) {
        return relatedRoadmap.getCustomRoadmapSpaceList().stream()
                .filter(CustomRoadmapSpace::isCompleted).count();
    }

    private List<CustomTemplate> createCustomTemplateList(List<Template> templateList, User user, Team team) {
        return templateList.stream()
                .map(template -> createCustomTemplate(template, user, team))
                .collect(Collectors.toList());
    }

    private CustomTemplate createCustomTemplate(Template template, User user, Team team) {
        TemplateDownload templateDownload = TemplateDownload.createTemplateDownload(user, template);
        CustomTemplate customTemplate = CustomTemplate.createCustomTemplate(template, templateDownload);
        TemplateContent templateContent = getTemplateContent(template.getId());
        saveTemplateDownload(templateDownload);
        saveCustomTemplate(customTemplate);
        saveCustomTemplateContent(template, templateContent);
        saveSearchCustomTemplate(user, customTemplate, team);
        return customTemplate;
    }

    private void saveCustomTemplateContent(Template template, TemplateContent templateContent) {
        CustomTemplateContent customTemplateContent = CustomTemplateContent.createCustomTemplateContent(template.getId(), templateContent.getContent());
        customTemplateContentRepository.save(customTemplateContent);
    }

    private void saveSearchCustomTemplate(User user, CustomTemplate customTemplate, Team team) {
        SearchUserTemplate searchCustomTemplate = SearchUserTemplate.of(user, customTemplate, team.getTitle());
        searchUserTemplateRepository.save(searchCustomTemplate);
    }

    private List<CustomRoadmapTemplate> createCustomRoadmapTemplateList(List<CustomTemplate> customTemplateList, CustomRoadmapSpace customRoadmapSpace) {
        return customTemplateList.stream()
                .map(customTemplate -> CustomRoadmapTemplate.createCustomRoadmapTemplate(customRoadmapSpace, customTemplate))
                .collect(Collectors.toList());
    }

    private List<Template> getTemplateListFromRoadmapTemplate(List<RoadmapTemplate> roadmapTemplateList) {
        return roadmapTemplateList.stream()
                .map(RoadmapTemplate::getTemplate)
                .collect(Collectors.toList());
    }

    private List<CustomRoadmapSpaceDetailResponseDto> createTeamRoadmapSpaceDetailResponseDto(List<CustomRoadmapSpace> teamRoadmapSpaceList) {
        return teamRoadmapSpaceList.stream()
                .map(customRoadmapSpace ->
                        CustomRoadmapSpaceDetailResponseDto.of(customRoadmapSpace, BaseCustomTemplateResponseDto.listOf(customRoadmapSpace)))
                .collect(Collectors.toList());
    }

    private void saveCustomRoadmapSpaceList(List<CustomRoadmapSpace> customRoadmapSpaceList) {
        customRoadmapSpaceRepository.saveAll(customRoadmapSpaceList);
    }

    private void saveCustomRoadmapTemplateList(List<CustomRoadmapTemplate> customRoadmapTemplateList) {
        customRoadmapTemplateRepository.saveAll(customRoadmapTemplateList);
    }

    private void saveTemplateDownload(TemplateDownload templateDownload) {
        templateDownloadRepository.save(templateDownload);
    }

    private TemplateContent getTemplateContent(Long templateId) {
        return templateContentRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_CONTENT_NOT_FOUND));
    }

    private void saveCustomTemplate(CustomTemplate customTemplate) {
        customTemplateRepository.save(customTemplate);
    }

    private void saveCustomRoadmap(CustomRoadmap customRoadmap) {
        customRoadmapRepository.save(customRoadmap);
    }

    private void saveRoadmapDownload(RoadmapDownload roadmapDownload) {
        roadmapDownloadRepository.save(roadmapDownload);
    }

    private Roadmap getRoadmapFromRoadmapId(Long roadmapId) {
        return roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_NOT_FOUND));
    }

    private Team getTeamFromTeamId(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    private User getUserFromUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
