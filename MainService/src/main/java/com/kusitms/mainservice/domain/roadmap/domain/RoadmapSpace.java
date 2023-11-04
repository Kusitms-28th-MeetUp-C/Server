package com.kusitms.mainservice.domain.roadmap.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "roadmap_space")
@Entity
public class RoadmapSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_detail_id")
    private Long id;
    private String title;
    private int step;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;
    @OneToMany(mappedBy = "roadmapSpace")
    @Builder.Default
    private List<RoadmapTemplate> RoadmapTemplateList = new ArrayList<>();
}
