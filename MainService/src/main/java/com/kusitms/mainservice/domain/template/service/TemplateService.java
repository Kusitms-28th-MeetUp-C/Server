package com.kusitms.mainservice.domain.template.service;


import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.team.dto.response.TeamTitleResponseDto;
import com.kusitms.mainservice.domain.team.repository.TeamRepository;
import com.kusitms.mainservice.domain.template.domain.*;
import com.kusitms.mainservice.domain.template.dto.request.SearchTemplateRequsetDto;
import com.kusitms.mainservice.domain.template.dto.response.*;
import com.kusitms.mainservice.domain.template.mongoRepository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.ReviewerRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.domain.user.dto.response.DetailUserResponseDto;
import com.kusitms.mainservice.domain.user.repository.UserRepository;
import com.kusitms.mainservice.global.error.ErrorCode;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.domain.template.domain.TemplateType.getEnumTemplateTypeFromStringTemplateType;
import static com.kusitms.mainservice.global.error.ErrorCode.TEMPLATE_NOT_FOUND;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final ReviewerRepository reviewerRepository;
    private final TemplateDownloadRepository templateDownloadRepository;
    private final RoadmapRepository roadmapRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TemplateManageService templateManageService;

    public Page<SearchBaseTemplateResponseDto> searchTemplateByTitleAndRoadmapType(SearchTemplateRequsetDto searchTemplateRequsetDto, Pageable pageable) {
        Page<Template> templateList = getTemplateListByTitleAndTemplateType(searchTemplateRequsetDto, pageable);
        return getTemplatesWithPaging(templateList);
    }

    public TemplateDetailResponseDto getTemplateDetail(Long templateId) {
        Template template = getTemplateByTemplateId(templateId);
        TemplateDetailIntroResponseDto templateDetailIntroResponseDto = createTemplateDetailIntroResponseDto(template);
        String templateContent = getTemplateContentByTemplateId(template).getContent();
        List<TemplateDetailBaseRelateDto> templateDetailBaseRelateDtoList = createTemplateDetailRelateTemplateDto(template);
        TemplateDetailConnectRoadmapDto roadmapIdAndConnectRoadmap = getRoadmapTitleResponseDto(template);
        DetailUserResponseDto detailUserResponseDto = createDetailUserResponseDto(template.getUser());
        return TemplateDetailResponseDto.of(template, templateDetailIntroResponseDto, templateContent, roadmapIdAndConnectRoadmap, templateDetailBaseRelateDtoList, detailUserResponseDto);

    }

    public GetTeamForSaveTemplateResponseDto getTeamForSaveTemplateByUserId(Long id) {
        List<Team> teams = teamRepository.findAllByUserId(id);
        List<TeamTitleResponseDto> teamTitleResponseDtoList = new ArrayList<>();
        for (Team team : teams) {
            List<TeamTitleResponseDto> titles = team.getTeamSpaceList()
                    .stream()
                    .map(teamSpace -> TeamTitleResponseDto.of(teamSpace.getTeam().getTitle()))
                    .collect(Collectors.toList());
            teamTitleResponseDtoList.addAll(titles);
        }
        //commit
        return GetTeamForSaveTemplateResponseDto.of(teamTitleResponseDtoList);
    }

    public String saveTemplateByUserId(Long userId, Long templateId) {
        Template template = getTemplateByTemplateId(templateId);
        Optional<User> user = userRepository.findById(userId);
        TemplateDownload templateDownload = TemplateDownload.createTemplateDownload(user.get(), template);
        templateDownloadRepository.save(templateDownload);
        return "저장";
    }

    private List<TemplateDetailBaseRelateDto> createTemplateDetailRelateTemplateDto(Template template) {
        List<Template> templateList = getTemplatesBySameCategoryAndId(template);
        return createTemplateDetailRelateTemplateDtoList(templateList);
    }

    private List<TemplateDetailBaseRelateDto> createTemplateDetailRelateTemplateDtoList(List<Template> templateList) {
        return templateList.stream()
                .map(template ->
                        TemplateDetailBaseRelateDto.of(
                                template,
                                template.getCount(),//getTeamCount(template),
                                getRatingAndReviewCount(template).getRatingAverage(),
                                getRoadmapTitleResponseDto(template).getConnectedRoadmap()))
                .collect(Collectors.toList());
    }

    private TemplateDetailIntroResponseDto createTemplateDetailIntroResponseDto(Template template) {
        TemplateDetailIntroBaseResponseDto templateDetailIntroBaseResponseDto = createTemplateDetailIntroBaseResponseDto(template);
        List<ReviewContentResponseDto> reviews = createReviewContentResponseDto(template);
        return TemplateDetailIntroResponseDto.of(template, templateDetailIntroBaseResponseDto, reviews);
    }

    private TemplateDetailIntroBaseResponseDto createTemplateDetailIntroBaseResponseDto(Template template) {
        TemplateReviewResponseDto templateReviewResponseDto = getRatingAndReviewCount(template);
        int teamCount = template.getCount();//getTeamCount(template);
        return TemplateDetailIntroBaseResponseDto.of(templateReviewResponseDto.getRatingAverage(), template.getEstimatedTime(), teamCount, templateReviewResponseDto.getReviewCount());
    }

    private TemplateContent getTemplateContentByTemplateId(Template template) {
        return templateManageService.getTemplateContentFromTemplateId(template.getId());
    }


    private Page<Template> getTemplateListByTitleAndTemplateType(SearchTemplateRequsetDto searchTemplateRequsetDto, Pageable pageable) {

        if (searchTemplateRequsetDto.getTemplateType() == null && !(searchTemplateRequsetDto.getTitle() == null)) {
            return getTemplateByTitle(searchTemplateRequsetDto.getTitle(), pageable);
        }
        if (!(searchTemplateRequsetDto.getTemplateType() == null) && searchTemplateRequsetDto.getTitle() == null) {
            return getTemplateFromTemplateType(searchTemplateRequsetDto.getTemplateType(), pageable);
        }
        return getTemplateByTitleAndTemplateType(searchTemplateRequsetDto, pageable);
    }

    //    private PageResponse getTemplate(List<Template> templateList){
//
//        return PageResponse.of()
//    }
    private Page<Template> getTemplateByTitleAndTemplateType(SearchTemplateRequsetDto searchTemplateRequsetDto, Pageable pageable) {
        String title = searchTemplateRequsetDto.getTitle();
        TemplateType templateType = getEnumTemplateTypeFromStringTemplateType(searchTemplateRequsetDto.getTemplateType());
        if (TemplateType.ALL.equals(templateType)) {
            return templateRepository.findByTitleContaining(searchTemplateRequsetDto.getTitle(), pageable);
        } else {
            return templateRepository.findByTitleContainingAndTemplateType(title, templateType, pageable);
        }
    }

    private Template getTemplateByTemplateId(Long templateId) {
       return templateRepository.findById(templateId).orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private Page<Template> getTemplateFromTemplateType(String stringTemplateType, Pageable pageable) {
        TemplateType templateType = getEnumTemplateTypeFromStringTemplateType(stringTemplateType);

        if (TemplateType.ALL.equals(templateType)) {
            return templateRepository.findAll(pageable);
        } else {
            return templateRepository.findByTemplateType(templateType, pageable);
        }
    }

    public Page<SearchBaseTemplateResponseDto> getTemplatesWithPaging(Page<Template> templatePage) {

        return templatePage.map(template ->
                SearchBaseTemplateResponseDto.of(
                        template,
                        template.getCount(),
                        getRoadmapTitleResponseDto(template).getConnectedRoadmap()
                )
        );
    }

    private TemplateDetailConnectRoadmapDto getRoadmapTitleResponseDto(Template template) {
        String title = null;
        Long roadmapId = null;

        List<RoadmapTemplate> roadmapTemplates = template.getRoadmapTemplates();

        if (!roadmapTemplates.isEmpty()) {
            Roadmap roadmap = roadmapTemplates.get(0).getRoadmapSpace().getRoadmap();
            if (roadmap != null) {
                title = roadmap.getTitle();
                roadmapId = roadmap.getId();
            }
        }

        return TemplateDetailConnectRoadmapDto.of(title, roadmapId);
    }

    private Page<Template> getTemplateByTitle(String title, Pageable pageable) {
        return templateRepository.findByTitleContaining(title, pageable);
    }

    private TemplateReviewResponseDto getRatingAndReviewCount(Template template) {
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
            return TemplateReviewResponseDto.of(totalRating / numRatings, reviewers.size());
        } else {
            return TemplateReviewResponseDto.of(0.0, 0);
        }
    }

    private int getTeamCount(Template template) {
        int tc = templateDownloadRepository.countDownloadsByTemplate(template);
        return tc;
    }

    private List<ReviewContentResponseDto> createReviewContentResponseDto(Template template) {
        List<Reviewer> reviewers = reviewerRepository.findByTemplate(template);
        List<ReviewContentResponseDto> reviewContentResponseDtoList = new ArrayList<>();
        for (Reviewer reviewer : reviewers) {
            if (reviewer.getTemplateReview() != null) {
                ReviewContentResponseDto reviewContentResponseDto = ReviewContentResponseDto.of(reviewer.getTemplateReview().getContent());
                reviewContentResponseDtoList.add(reviewContentResponseDto);
            }
        }
        return reviewContentResponseDtoList;
    }

    private DetailUserResponseDto createDetailUserResponseDto(User user) {
        int templateNum = getTemplateCountByUser(user);
        int roadmapNum = getRoadmapCountByUser(user);
        return DetailUserResponseDto.of(user, templateNum, roadmapNum);
    }

    private int getTemplateCountByUser(User user) {
        return templateRepository.countByUser(user);
    }

    private int getRoadmapCountByUser(User user) {
        return roadmapRepository.countByUser(user);
    }

    private List<Template> getTemplatesBySameCategoryAndId(Template template) {
        return templateRepository.findTop4ByTemplateTypeAndIdNot(template.getTemplateType(), template.getId());
    }
}
