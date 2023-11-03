package com.kusitms.mainservice.domain.template.service;

import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateContent;
import com.kusitms.mainservice.domain.template.domain.TemplateData;
import com.kusitms.mainservice.domain.template.repository.TemplateContentRepository;
import com.kusitms.mainservice.domain.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private final TemplateContentRepository templateContentRepository;

    public List<TemplateData> getAllData(Long id) {
        List<Template> mySQLDataList = templateRepository.findAll();
        List<TemplateData> templateData = new ArrayList<>();

        for (Template template : mySQLDataList) {
            TemplateContent templateContent = templateContentRepository.findById(id.toString()).orElse(null);
            if (templateContent != null) {
                templateData.add(new TemplateData(template, templateContent));
            }
        }

        return templateData;
    }
}
