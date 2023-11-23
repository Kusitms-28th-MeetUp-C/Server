package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.template.domain.CustomTemplate;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "custom_roadmap_template")
@Entity
public class CustomRoadmapTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_roadmap_template_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "custom_roadmap_space_id")
    private CustomRoadmapSpace customRoadmapSpace;
    @ManyToOne
    @JoinColumn(name = "custom_template_id")
    private CustomTemplate customTemplate;

    public static CustomRoadmapTemplate createCustomRoadmapTemplate(CustomRoadmapSpace customRoadmapSpace,
                                                                    CustomTemplate customTemplate) {
        CustomRoadmapTemplate customRoadmapTemplate = CustomRoadmapTemplate.builder()
                .customRoadmapSpace(customRoadmapSpace)
                .customTemplate(customTemplate)
                .build();
        customRoadmapSpace.addCustomRoadmapTemplate(customRoadmapTemplate);
        customTemplate.addCustomRoadmapTemplate(customRoadmapTemplate);
        return customRoadmapTemplate;
    }
}
