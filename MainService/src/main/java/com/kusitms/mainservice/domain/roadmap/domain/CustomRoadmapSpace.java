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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_roadmap_id")
    private CustomRoadmap customRoadmap;
    @OneToMany(mappedBy = "customRoadmapSpace")
    @Builder.Default
    private List<CustomRoadmapTemplate> customRoadmapTemplateList = new ArrayList<>();
}
