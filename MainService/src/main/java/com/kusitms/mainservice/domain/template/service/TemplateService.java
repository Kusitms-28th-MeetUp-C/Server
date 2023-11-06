package com.kusitms.mainservice.domain.template.service;


import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.domain.*;
import com.kusitms.mainservice.domain.template.dto.response.*;
import com.kusitms.mainservice.domain.template.repository.ReviewerRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;

import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.global.error.ErrorCode.TEMPLATE_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateContentRepository templateContentRepository;
    private final ReviewerRepository reviewerRepository;
    private final TemplateDownloadRepository templateDownloadRepository;
    private final RoadmapRepository roadmapRepository;
    public SearchTemplateResponseDto searchTemplatesByCategory(TemplateType templateType) {
        if(TemplateType.ALL.equals(templateType)) {
            List<Template> templateList = templateRepository.findAll();
            List<SearchBaseTemplateResponseDto> searchbaseTemplateResponseDtoList = createSearchBaseTemplateResponseDtoList(templateList);
            return SearchTemplateResponseDto.of(searchbaseTemplateResponseDtoList);
        }
        else {
        List<Template> templateList = getTemplateFromTemplateType(templateType);
        List<SearchBaseTemplateResponseDto> searchbaseTemplateResponseDtoList = createSearchBaseTemplateResponseDtoList(templateList);
        return SearchTemplateResponseDto.of(searchbaseTemplateResponseDtoList);
        }
    }
    public SearchTemplateResponseDto searchTemplatesByTitle(String title) {
        List<Template> templateList = getTemplateByTitle(title);
        List<SearchBaseTemplateResponseDto> searchbaseTemplateResponseDtoList= createSearchBaseTemplateResponseDtoList(templateList);
        return SearchTemplateResponseDto.of(searchbaseTemplateResponseDtoList);
    }
    public TemplateDetailResponseDto getTemplateDetail(Long templateId){
        Template template = templateRepository.findByTemplateId(templateId);
        TemplateContent templateContent = templateContentRepository.findByTemplateId(templateId);
        List<Template> templateList = templateRepository.findAllByTemplateType(template.getTemplateType());
        List<RoadmapTitleResponseDto> roadmapTitleResponseDtoList = createRoadmapTitleResponseDto(templateList);
        List<SearchBaseTemplateResponseDto> relatedTemplate = createSearchBaseTemplateResponseDtoList(templateList);
        RatingResponseDto ratingResponseDto = createRatingResponse(template);
        int teamCount = getTeamCount(template);
        List<ReviewContentResponseDto> reviewContentResponseDtoList = createReviewContentResponseDto(template);
        TemplateDetailUserResponseDto templateDetailUserResponseDto =createTemplateDetailUserResponseDto(templateId);
        return TemplateDetailResponseDto.of(template, templateContent,roadmapTitleResponseDtoList,relatedTemplate,ratingResponseDto, teamCount,reviewContentResponseDtoList, templateDetailUserResponseDto );
    }
    private List<Template> getTemplateFromTemplateType(TemplateType templateType) {
        return templateRepository.findAllByTemplateType(templateType);
    }
    private List<SearchBaseTemplateResponseDto> createSearchBaseTemplateResponseDtoList(List<Template> templateList) {
        return templateList.stream()
                .map(template ->
                        SearchBaseTemplateResponseDto.of(
                                template,
                                createRoadmapTitleResponseDto(templateList)))
                .collect(Collectors.toList());
    }
    private  List<RoadmapTitleResponseDto> createRoadmapTitleResponseDto(List<Template> templateList){
        List<RoadmapTitleResponseDto> roadmapTitleResponseDtoList = new ArrayList<>();

        for (Template template : templateList) {
            List<RoadmapTitleResponseDto> titles = template.getRoadmapTemplates()
                    .stream()
                    .map(roadmapTemplate -> RoadmapTitleResponseDto.of(roadmapTemplate.getRoadmapSpace().getTitle()))
                    .collect(Collectors.toList());

            roadmapTitleResponseDtoList.addAll(titles);
        }

        return roadmapTitleResponseDtoList;
    }
    private List<Template> getTemplateByTitle(String title) {
        return templateRepository.findByTitleContaining(title);
    }
    private RatingResponseDto createRatingResponse(Template template) {
        List<Reviewer> reviewers = reviewerRepository.findByTemplate(template);
        Double totalRating = 0.0;
        int numRatings = 0;

        for (Reviewer reviewer : reviewers) {
            if (reviewer.getTemplateReview() != null) {
                totalRating = totalRating + reviewer.getTemplateReview().getRating();
                numRatings++;
            }
        }

        if (numRatings > 0) {
            return RatingResponseDto.of(totalRating / numRatings);
        } else {
            return RatingResponseDto.of(0.0);
        }
    }
    private int getTeamCount(Template template){
        int tc = templateDownloadRepository.countUsersByTemplate(template);
        return tc;
    }
    private List<ReviewContentResponseDto> createReviewContentResponseDto(Template template){
        List<Reviewer> reviewers = reviewerRepository.findByTemplate(template);
        List<ReviewContentResponseDto> reviewContentResponseDtoList = new ArrayList<>();
        for (Reviewer reviewer : reviewers) {
            if (reviewer.getTemplateReview() != null) {
                ReviewContentResponseDto reviewContentResponseDto=ReviewContentResponseDto.of(reviewer.getTemplateReview().getContent());
                reviewContentResponseDtoList.add(reviewContentResponseDto);
            }
        }
        return reviewContentResponseDtoList;
    }
    private TemplateDetailUserResponseDto createTemplateDetailUserResponseDto(Long templateId){
        User user = templateRepository.findUserByTemplateId(templateId);
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return TemplateDetailUserResponseDto.of(user, templateNum,roadmapNum);
    }
    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }
    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }
}
