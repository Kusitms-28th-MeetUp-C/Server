package com.kusitms.mainservice.domain.template.service;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapType;
import com.kusitms.mainservice.domain.roadmap.dto.response.RelatedRoadmapResponseDto;
import com.kusitms.mainservice.domain.roadmap.dto.response.RoadmapTitleResponseDto;
import com.kusitms.mainservice.domain.roadmap.repository.RoadmapRepository;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.response.BaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.OriginalTemplateDetailResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.RelatedTemplateResponseDto;
import com.kusitms.mainservice.domain.template.dto.response.UserBaseTemplateResponseDto;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateDownloadRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import com.kusitms.mainservice.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms.mainservice.domain.roadmap.domain.RoadmapType.getEnumPeriodFromStringPeriod;
import static com.kusitms.mainservice.global.error.ErrorCode.TEMPLATE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TemplateUserService {
    private final TemplateDownloadRepository templateDownloadRepository;
    private final TemplateContentRepository templateContentRepository;
    private final RoadmapRepository roadmapRepository;
    private final TemplateRepository templateRepository;

    public UserBaseTemplateResponseDto getTemplateListOfUser(Long userId) {
        List<TemplateDownload> downloadTemplateList = getTemplateDownloadList(userId);
        List<BaseTemplateResponseDto> baseTemplateResponseDtoList = createBaseTemplateResponseDtoList(downloadTemplateList);
        return UserBaseTemplateResponseDto.of(baseTemplateResponseDtoList);
    }

    public OriginalTemplateDetailResponseDto getOriginalTemplateOfUser(Long templateId) {
        Template template = getTemplateFromTemplateId(templateId);
        String templateContents = getTemplateContents(templateId);
        List<Template> templateList = getTemplateListOfSameCategory(template.getTemplateType());
        List<RelatedTemplateResponseDto> relatedTemplateResponseDtoList = createRelatatedTemplateList(templateList);
        RoadmapType roadmapType = getEnumPeriodFromStringPeriod(template.getTemplateType().getStringTemplateType());
        List<Roadmap> roadmapList = getRoadmapListOfSameCategory(roadmapType);
        List<RelatedRoadmapResponseDto> relatedRoadmapResponseDtoList = createRelatedRoadmapList(roadmapList);
        return OriginalTemplateDetailResponseDto.of(template, templateContents, relatedTemplateResponseDtoList, relatedRoadmapResponseDtoList);
    }

    private List<Roadmap> getRoadmapListOfSameCategory(RoadmapType roadmapType) {
        return roadmapRepository.findTop3ByRoadmapType(roadmapType);
    }

    private List<Template> getTemplateListOfSameCategory(TemplateType templateType) {
        return templateRepository.findTop3ByTemplateType(templateType);
    }

    private List<RelatedRoadmapResponseDto> createRelatedRoadmapList(List<Roadmap> roadmapList) {
        return roadmapList.stream()
                .map(RelatedRoadmapResponseDto::of)
                .collect(Collectors.toList());
    }

    private List<RelatedTemplateResponseDto> createRelatatedTemplateList(List<Template> templateList) {
        return templateList.stream()
                .map(RelatedTemplateResponseDto::of)
                .collect(Collectors.toList());
    }

    private List<Template> createTemplateListFromDownloadTemplate(List<TemplateDownload> downloadTemplateList, Template template) {
        List<Template> templateList = getTemplateListFromTemplateDownload(downloadTemplateList);
        return templateList.stream()
                .filter(currentTemplate -> validateSameTemplateType(currentTemplate, template))
                .collect(Collectors.toList());
    }

    private boolean validateSameTemplateType(Template currentTemplate, Template template) {
        return currentTemplate.getTemplateType() == template.getTemplateType() && currentTemplate.getId() != template.getId();
    }

    private List<Template> getTemplateListFromTemplateDownload(List<TemplateDownload> downloadTemplateList) {
        return downloadTemplateList.stream()
                .map(TemplateDownload::getTemplate)
                .collect(Collectors.toList());
    }

    private String getTemplateContents(Long templateId) {
        TemplateContent templateContent = getTemplateContent(templateId);
        return templateContent.getContent();
    }

    private TemplateContent getTemplateContent(Long templateId) {
        return templateContentRepository.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private Template getTemplateFromTemplateId(Long templateId) {
        return templateRepository.findById(templateId).orElseThrow(() -> new EntityNotFoundException(TEMPLATE_NOT_FOUND));
    }

    private List<BaseTemplateResponseDto> createBaseTemplateResponseDtoList(List<TemplateDownload> downloadTemplateList) {
        return downloadTemplateList.stream()
                .map(downloadTemplate ->
                        BaseTemplateResponseDto.of(
                                downloadTemplate.getTemplate(),
                                createRoadmapTitleResponseDto(downloadTemplate)))
                .collect(Collectors.toList());
    }

    private List<RoadmapTitleResponseDto> createRoadmapTitleResponseDto(TemplateDownload templateDownload) {
        List<RoadmapTemplate> roadmapTemplateList = templateDownload.getRoadmapTemplateList();
        return roadmapTemplateList.stream()
                .map(roadmapTemplate -> RoadmapTitleResponseDto.of(roadmapTemplate.getRoadmapDetail().getTitle()))
                .collect(Collectors.toList());
    }


    private List<TemplateDownload> getTemplateDownloadList(Long userId) {
        return templateDownloadRepository.findAllByUserId(userId);
    }
}
