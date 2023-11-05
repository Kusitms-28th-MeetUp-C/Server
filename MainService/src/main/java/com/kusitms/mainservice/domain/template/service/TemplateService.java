package com.kusitms.mainservice.domain.template.service;


import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.template.domain.*;
import com.kusitms.mainservice.domain.template.dto.response.*;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;

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

    public SearchTemplateResponseDto searchTemplatesByCategory(TemplateType templateType) {
        List<Template> templateList = getTemplateFromTemplateType(templateType);
        List<SearchBaseTemplateResponseDto> searchbaseTemplateResponseDtoList= createSearchBaseTemplateResponseDtoList(templateList);
        return SearchTemplateResponseDto.of(searchbaseTemplateResponseDtoList);
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
}
