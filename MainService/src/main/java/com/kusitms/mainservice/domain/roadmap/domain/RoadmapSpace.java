package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.roadmap.dto.request.StepDto;
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
    public static RoadmapSpace createRoadmapSpace(StepDto stepDto, Roadmap roadmap, int step){
        RoadmapSpace roadmapSpace = RoadmapSpace.builder()
                .title(stepDto.getStepTitle())
                .step(step)
                .roadmap(roadmap)
                .build();
        roadmap.addRoadmapSpace(roadmapSpace);
        return roadmapSpace;
    }
    public void addRoadmapTemplate(RoadmapTemplate roadmapTemplate){
        this.RoadmapTemplateList.add(roadmapTemplate);
    }
}
