package com.kusitms.mainservice.domain.roadmap.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "custom_roadmap_space")
@Entity
public class CustomRoadmapSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_roadmap_detail_id")
    private Long id;
    private String title;
    private int step;
    private LocalDate startDate;
    private LocalDate endDate;
    @Builder.Default
    private boolean isCompleted = false;
    private String introduction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_roadmap_id")
    private CustomRoadmap customRoadmap;
    @OneToMany(mappedBy = "customRoadmapSpace")
    @Builder.Default
    private List<CustomRoadmapTemplate> customRoadmapTemplateList = new ArrayList<>();

    public static CustomRoadmapSpace createCustomRoadmapSpace(RoadmapSpace roadmapSpace, CustomRoadmap customRoadmap){
        CustomRoadmapSpace customRoadmapSpace =  CustomRoadmapSpace.builder()
                .title(roadmapSpace.getTitle())
                .step(roadmapSpace.getStep())
                .startDate(LocalDate.now())
                .endDate(null)
                .isCompleted(false)
                .introduction(null)
                .customRoadmap(customRoadmap)
                .build();
        customRoadmap.addCustomRoadmapSpace(customRoadmapSpace);
        return customRoadmapSpace;
    }

    public void addCustomRoadmapTemplate(CustomRoadmapTemplate customRoadmapTemplate){
        this.customRoadmapTemplateList.add(customRoadmapTemplate);
    }

    public void updateCompletedAneEndTime(boolean isCompleted){
        this.isCompleted = isCompleted;
        if(isCompleted) this.updateEndTime(LocalDate.now());
        else this.updateEndTime(null);
    }

    public void updateEndTime(LocalDate endDate){
        this.endDate = endDate;
    }
}
