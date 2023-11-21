package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapTemplate;
import com.kusitms.mainservice.domain.template.dto.request.UpdateTemplateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "custom_template")
@Entity
public class CustomTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_tamplate_id")
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TemplateType templateType;
    @OneToMany(mappedBy = "customTemplate")
    @Builder.Default
    List<CustomRoadmapTemplate> customRoadmapTemplateList = new ArrayList<>();
    @OneToOne
    private TemplateDownload templateDownload;

    public static CustomTemplate createCustomTemplate(Template template, TemplateDownload templateDownload){
        CustomTemplate customTemplate = CustomTemplate.builder()
                .title(template.getTitle())
                .templateType(template.getTemplateType())
                .templateDownload(templateDownload)
                .build();
        templateDownload.addCustomTemplate(customTemplate);
        return customTemplate;
    }

    public void addCustomRoadmapTemplate(CustomRoadmapTemplate customRoadmapTemplate){
        this.customRoadmapTemplateList.add(customRoadmapTemplate);
    }
}
