package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.roadmap.domain.CustomRoadmapTemplate;
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
}
