package com.kusitms.mainservice.domain.template.service;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmap;
import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapSpace;
import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.CustomRoadmapSpaceDetailResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapRepository;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapSpaceRepository;
import com.kusitms.mainservice.domain.roadmap.repository.CustomRoadmapTemplateRepository;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.dto.response.TeamResponseDto;
import com.kusitms.mainservice.domain.team.dto.response.TeamSpaceResponseDto;
import com.kusitms.mainservice.domain.team.repository.TeamRepository;
import com.kusitms.mainservice.domain.template.domain.*;
import com.kusitms.mainservice.domain.template.dto.request.TemplateReviewRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateSharingRequestDto;
import com.kusitms.mainservice.domain.template.dto.request.TemplateTeamRequestDto;
import com.kusitms.mainservice.domain.template.dto.response.BaseCustomTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.CreateTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.CustomTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.TemplateDownloadDetailResponseDto;
import com.kusitms.mainservice.domain.template.mongoRepository.CustomTemplateContentRepository;
import com.kusitms.mainservice.domain.template.mongoRepository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.*;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.domain.template.domain.TemplateContent.createTemplateContent;
import static com.kusitms.mainservice.domain.template.domain.TemplateType.getEnumTemplateTypeFromStringTemplateType;
import static com.kusitms.mainservice.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TemplateManageService {
    private final TemplateRepository templateRepository;
    private final TemplateContentRepository templateContentRepository;
    private final CustomTemplateContentRepository customTemplateContentRepository;
    private final CustomTemplateRepository customTemplateRepository;
    private final CustomRoadmapRepository customRoadmapRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final TemplateDownloadRepository templateDownloadRepository;
    private final TemplateReviewRepository templateReviewRepository;
    private final ReviewerRepository reviewerRepository;
    private final CustomRoadmapSpaceRepository customRoadmapSpaceRepository;
    private final CustomRoadmapTemplateRepository customRoadmapTemplateRepository;

    public void addTemplateToTeam(Long userId, TemplateTeamRequestDto templateTeamRequestDto) {
        Template template = getTemplateFromTemplateId(templateTeamRequestDto.getTemplateId());
        TemplateDownload templateDownload = getTemplateDownloadFromUserIdAndTemplateId(userId, templateTeamRequestDto.getTemplateId());
        CustomTemplate customTemplate = CustomTemplate.createCustomTemplate(template, templateDownload);
        saveCustomTemplate(customTemplate);
        CustomRoadmapSpace customRoadmapSpace = getCustomRoadmapSpaceFromStepId(templateTeamRequestDto.getStepId());
        CustomRoadmapTemplate customRoadmapTemplate = CustomRoadmapTemplate.createCustomRoadmapTemplate(customRoadmapSpace, customTemplate);
        saveCustomRoadmapTemplate(customRoadmapTemplate);
        saveCustomTemplateContent(template, customTemplate);
    }

    public CreateTemplateResponseDto createSharingTemplate(Long userId, TemplateSharingRequestDto templateSharingRequestDto) {
        User user = getUserFromUserId(userId);
        TemplateType templateType = getEnumTemplateTypeFromStringTemplateType(templateSharingRequestDto.getTemplateType());
        Template createdTemplate = Template.createTemplate(templateSharingRequestDto, templateType, user);
        saveTemplate(createdTemplate);
        TemplateContent templateContent = createTemplateContent(createdTemplate.getId(), templateSharingRequestDto.getContent());
        saveTemplateContent(templateContent);
        return CreateTemplateResponseDto.of(createdTemplate);
    }

    public CustomTemplateDetailResponseDto getTeamTemplateDetailInfo(Long userId, String roadmapTitle, String teamTitle, Long templateId) {
        CustomTemplate customTemplate = getCustomTemplateFromTemplateId(templateId);
        CustomTemplateContent templateContent = getCustomTemplateContentFromTemplateId(templateId);
        CustomRoadmap relatedRoadmap = getCustomRoadmapFromUserIdAndTitle(userId, roadmapTitle);
        CustomRoadmapDetailResponseDto baseRoadmapResponseDto = createCustomRoadmapDetailResponseDto(relatedRoadmap);
        Team team = getTeamFromTitleAndUserId(userId, teamTitle);
        TeamResponseDto teamResponseDto = createTeamResponseDto(team);
        return CustomTemplateDetailResponseDto.of(customTemplate, templateContent.getContent(), baseRoadmapResponseDto, teamResponseDto);
    }

    public TemplateDownloadDetailResponseDto getPreDownloadTemplateInfo(Long templateId) {
        Template template = getTemplateFromTemplateId(templateId);
        TemplateContent templateContent = getTemplateContentFromTemplateId(templateId);
        return TemplateDownloadDetailResponseDto.ofTemplate(template, templateContent.getContent());
    }

    public TemplateDownloadDetailResponseDto getDownloadCustomTemplateDetailInfo(Long templateId) {
        CustomTemplate customTemplate = getCustomTemplateFromTemplateId(templateId);
        CustomTemplateContent templateContent = getCustomTemplateContentFromTemplateId(templateId);
        return TemplateDownloadDetailResponseDto.ofCustomTemplate(customTemplate, templateContent.getContent());
    }

    public void createTemplateReview(Long userId, TemplateReviewRequestDto templateReviewRequestDto) {
        User user = getUserFromUserId(userId);
        Template template = getTemplateFromTemplateId(templateReviewRequestDto.getTemplateId());
        TemplateReview createdTemplateReview = TemplateReview.createTemplateReview(templateReviewRequestDto.getContent(), templateReviewRequestDto.getRating());
        Reviewer createdReviewer = Reviewer.createReviewer(user, createdTemplateReview, template);
        saveReviewer(createdReviewer);
        saveTemplateReview(createdTemplateReview);
    }

    public void saveCustomTemplateContent(Template template, CustomTemplate customTemplate) {
        TemplateContent templateContent = getTemplateContentFromTemplateId(template.getId());
        CustomTemplateContent customTemplateContent
                = CustomTemplateContent.createCustomTemplateContent(customTemplate.getId(), templateContent.getContent());
        saveCustomTemplateContent(customTemplateContent);
    }
    public void deleteTemplateByTemplateId(Long templateId){
        deleteTemplate(templateId);
    }
    private void deleteTemplate(Long templateId){
        templateRepository.deleteById(templateId);
    }

    private TeamResponseDto createTeamResponseDto(Team team) {
        List<TeamSpaceResponseDto> teamSpaceResponseDtoList = TeamSpaceResponseDto.listOf(team.getTeamSpaceList());
        return TeamResponseDto.of(team, teamSpaceResponseDtoList);
    }

    private CustomRoadmapDetailResponseDto createCustomRoadmapDetailResponseDto(CustomRoadmap relatedRoadmap) {
        List<CustomRoadmapSpaceDetailResponseDto> customRoadmapSpaceDetailList
                = createTeamRoadmapSpaceDetailResponseDto(relatedRoadmap.getCustomRoadmapSpaceList());
        Long processingNum = getProcessingNum(relatedRoadmap);
        return CustomRoadmapDetailResponseDto.of(relatedRoadmap, processingNum.intValue(), customRoadmapSpaceDetailList);
    }

    private List<CustomRoadmapSpaceDetailResponseDto> createTeamRoadmapSpaceDetailResponseDto(List<CustomRoadmapSpace> teamRoadmapSpaceList) {
        return teamRoadmapSpaceList.stream()
                .map(customRoadmapSpace ->
                        CustomRoadmapSpaceDetailResponseDto.of(customRoadmapSpace, BaseCustomTemplateResponseDto.listOf(customRoadmapSpace)))
                .collect(Collectors.toList());
    }

    private Long getProcessingNum(CustomRoadmap relatedRoadmap){
        return relatedRoadmap.getCustomRoadmapSpaceList().stream()
                .filter(CustomRoadmapSpace::isCompleted).count();
    }

    private CustomRoadmapSpace getCustomRoadmapSpaceFromStepId(Long stepId) {
        return customRoadmapSpaceRepository.findById(stepId)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_SPACE_NOT_FOUND));
    }

    private TemplateDownload getTemplateDownloadFromUserIdAndTemplateId(Long userId, Long templateId) {
        return templateDownloadRepository.findByUserIdAndTemplateId(userId, templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private Team getTeamFromTitleAndUserId(Long userId, String title) {
        return teamRepository.findByUserIdAndTitle(userId, title)
                .orElseThrow(() -> new EntityNotFoundException(TEAM_NOT_FOUND));
    }

    private CustomTemplateContent getCustomTemplateContentFromTemplateId(Long templateId) {
        return customTemplateContentRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_CONTENT_NOT_FOUND));
    }

    private CustomRoadmap getCustomRoadmapFromUserIdAndTitle(Long userId, String title) {
        return customRoadmapRepository.findByUserIdAndTitle(userId, title)
                .orElseThrow(() -> new EntityNotFoundException(ROADMAP_NOT_FOUND));
    }

    private CustomTemplate getCustomTemplateFromTemplateId(Long templateId) {
        return customTemplateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    public TemplateContent getTemplateContentFromTemplateId(Long templateId) {
        return templateContentRepository.findByTemplateId(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_CONTENT_NOT_FOUND));
    }

    private Template getTemplateFromTemplateId(Long templateId) {
        return templateRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private User getUserFromUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }

    private void saveCustomTemplateContent(CustomTemplateContent customTemplateContent) {
        customTemplateContentRepository.save(customTemplateContent);
    }

    private void saveCustomTemplate(CustomTemplate customTemplate) {
        customTemplateRepository.save(customTemplate);
    }

    private void saveCustomRoadmapTemplate(CustomRoadmapTemplate customRoadmapTemplate) {
        customRoadmapTemplateRepository.save(customRoadmapTemplate);
    }

    private void saveTemplateContent(TemplateContent templateContent) {
        templateContentRepository.save(templateContent);
    }

    private void saveTemplate(Template template) {
        templateRepository.save(template);
    }

    private void saveReviewer(Reviewer reviewer) {
        reviewerRepository.save(reviewer);
    }

    private void saveTemplateReview(TemplateReview templateReview) {
        templateReviewRepository.save(templateReview);
    }
}
