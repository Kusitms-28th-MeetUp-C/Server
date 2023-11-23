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
@Table(name = "custom_roadmap")
@Entity
public class CustomRoadmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_roadmap_id")
    private Long id;
    private String title;
    private String goal;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(value = EnumType.STRING)
    private RoadmapType roadmapType;
    @OneToOne
    private RoadmapDownload roadmapDownload;
    @OneToMany(mappedBy = "customRoadmap")
    @Builder.Default
    private List<CustomRoadmapSpace> customRoadmapSpaceList = new ArrayList<>();

    public static CustomRoadmap createCustomRoadmap(Roadmap roadmap, RoadmapDownload roadmapDownload) {
        CustomRoadmap customRoadmap = CustomRoadmap.builder()
                .title(roadmap.getTitle())
                .startDate(LocalDate.now())
                .endDate(null)
                .roadmapType(roadmap.getRoadmapType())
                .roadmapDownload(roadmapDownload)
                .build();
        roadmapDownload.addCustomRoadmap(customRoadmap);
        return customRoadmap;
    }

    public void updateEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void addCustomRoadmapSpace(CustomRoadmapSpace customRoadmapSpace) {
        this.customRoadmapSpaceList.add(customRoadmapSpace);
    }
}
