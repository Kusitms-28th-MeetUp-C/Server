package com.kusitms.mainservice.domain.template.service;


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
import com.kusitms.mainservice.domain.user.service.AuthService;
import com.kusitms.mainservice.domain.user.service.UserService;
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
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public Page<SearchBaseTemplateResponseDto> searchTemplateByTitleAndRoadmapType(SearchTemplateRequsetDto searchTemplateRequsetDto,Pageable pageable){
        Page<Template> templateList = getTemplateListByTitleAndTemplateType(searchTemplateRequsetDto, pageable);
        Page<SearchBaseTemplateResponseDto> searchBaseTemplateResponseDtoList = getTemplatesWithPaging(templateList,pageable);
//        return SearchTemplateResponseDto.of(searchBaseTemplateResponseDtoList);
        return searchBaseTemplateResponseDtoList;
    }

    public TemplateDetailResponseDto getTemplateDetail(Long templateId) {
        Template template = getTemplateByTemplateId(templateId);
        TemplateDetailIntroResponseDto templateDetailIntroResponseDto = createTemplateDetailIntroResponseDto(template);
        List<TemplateContent> templateContentList = getTemplateContentListByTemplateId(template);
        List<TemplateDetailBaseRelateDto> templateDetailBaseRelateDtoList = createTemplateDetailRelateTemplateDto(template);
        TemplateDetailConnectRoadmapDto roadmapIdAndConnectRoadmap = getRoadmapTitleResponseDto(template);
        DetailUserResponseDto detailUserResponseDto =createDetailUserResponseDto(template.getUser());
        return TemplateDetailResponseDto.of(template,templateDetailIntroResponseDto ,templateContentList,roadmapIdAndConnectRoadmap, templateDetailBaseRelateDtoList,detailUserResponseDto);

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

    @Transactional
    public String saveTemplateByUserId(SaveTemplateResponseDto saveTemplateResponseDto){
        Template template = getTemplateByTemplateId(saveTemplateResponseDto.getTemplateid());
        Optional<User> user = userRepository.findById(saveTemplateResponseDto.getUserid());
        TemplateDownload templateDownload = TemplateDownload.createTemplateDownload(user.get(),template);
        templateDownloadRepository.save(templateDownload);
        return "저장";
    }

    private List<TemplateDetailBaseRelateDto> createTemplateDetailRelateTemplateDto(Template template){
        List<Template> templateList = getTemplatesBySameCategoryAndId(template);
        List<TemplateDetailBaseRelateDto> templateDetailBaseRelateDtoList = createTemplateDetailRelateTemplateDtoList(templateList);
        return templateDetailBaseRelateDtoList;
    }

    private List<TemplateDetailBaseRelateDto> createTemplateDetailRelateTemplateDtoList(List<Template> templateList) {
        return templateList.stream()
                .map(template ->
                        TemplateDetailBaseRelateDto.of(
                                template,
                                template.getCount(),//getTeamCount(template),
                                getRatingAndReviewCount(template).getRatingAverage()))
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

private List<TemplateContent> getTemplateContentListByTemplateId(Template template) {
    List<TemplateContent> templateContentList = templateContentRepository.findAllByTemplateId(template.getId());


//    List<TemplateContent> filteredList = templateContentList.stream()
//            .filter(tc -> tc.getAgendaNum() != null)
//            .collect(Collectors.toList());
//
//
//    filteredList.sort(Comparator.comparing(TemplateContent::getAgendaNum));
//
//    Map<Long, List<Map<String, String>>> result = new LinkedHashMap<>();
//    List<Map<String, String>> contentList = new ArrayList<>();
//
//    for (TemplateContent templateContent : filteredList) {
//        Map<String, String> contentMap = new LinkedHashMap<>();
//        contentMap.put("agendaNum", String.valueOf(templateContent.getAgendaNum()));
//        contentMap.put("agenda", templateContent.getAgenda());
//        contentMap.put("content", templateContent.getContent());
//        contentList.add(contentMap);
//    }
//
//    result.put(template.getId(), contentList);


    return templateContentList;
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
            Page<Template> templateList = templateRepository.findByTitleContainingAndTemplateType(title, templateType, pageable);
            return templateList;
        }
    }

    private Template getTemplateByTemplateId(Long templateId) {
        Optional<Template> template = templateRepository.findById(templateId);
        return template.get();
    }

    private Page<Template> getTemplateFromTemplateType(String stringTemplateType, Pageable pageable) {
        TemplateType templateType = getEnumTemplateTypeFromStringTemplateType(stringTemplateType);

        if (TemplateType.ALL.equals(templateType)) {
            return templateRepository.findAll(pageable);
        } else {
            return templateRepository.findByTemplateType(templateType, pageable);
        }
    }

    public Page<SearchBaseTemplateResponseDto> getTemplatesWithPaging(Page<Template> templatePage, Pageable pageable) {

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
        List<RoadmapTemplate> roadmapTemplates = template.getRoadmapTemplates();
        if (roadmapTemplates.size() != 0) {
            title = roadmapTemplates.get(0).getRoadmapSpace().getRoadmap().getTitle();
        }
        //        List<RoadmapTitleResponseDto> roadmapTitleResponseDtoList = new ArrayList<>();
//
//        List<RoadmapTemplate> roadmapTemplates = template.getRoadmapTemplates();
//        for (RoadmapTemplate roadmapTemplate : roadmapTemplates) {
//            String title = roadmapTemplate.getRoadmapSpace().getRoadmap().getTitle();
//            RoadmapTitleResponseDto titleResponseDto = RoadmapTitleResponseDto.of(title);
//            roadmapTitleResponseDtoList.add(titleResponseDto);
//
//
//        }
        return TemplateDetailConnectRoadmapDto.of(title,roadmapTemplates.get(0).getRoadmapSpace().getRoadmap().getId());
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

    private DetailUserResponseDto createDetailUserResponseDto(User user){
        return userService.createDetailUserResponseDto(user);

    }

    private List<Template> getTemplatesBySameCategoryAndId(Template template){
//
        List<Template> templates = templateRepository.findTop4ByTemplateTypeAndIdNot(template.getTemplateType(), template.getId());
        return templates;
    }
}
