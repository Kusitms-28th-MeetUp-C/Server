package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.roadmap.dto.request.RoadmapSharingRequestDto;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateType;
import com.kusitms.mainservice.domain.template.dto.request.TemplateSharingRequestDto;
import com.kusitms.mainservice.domain.user.domain.User;
import com.kusitms.mainservice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "roadmap")
@Entity
public class Roadmap extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long id;
    private String title;
    @Builder.Default
    private int count = 0;
    private String introduction;
    @Enumerated(value = EnumType.STRING)
    private RoadmapType roadmapType;
    @OneToMany(mappedBy = "roadmap")
    @Builder.Default
    private List<RoadmapDownload> roadmapDownloadList = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User user;
    @OneToMany(mappedBy = "roadmap")
    @Builder.Default
    private List<RoadmapSpace> roadmapSpaceList = new ArrayList<>();

    public void addRoadmapDownload(RoadmapDownload roadmapDownload){
        this.roadmapDownloadList.add(roadmapDownload);
    }
    public void addRoadmapSpace(RoadmapSpace roadmapSpace){
        this.roadmapSpaceList.add(roadmapSpace);
    }
    public static Roadmap createRoadmap(RoadmapSharingRequestDto roadmapSharingRequestDto, RoadmapType roadmapType, User user){
        Roadmap roadmap = Roadmap.builder()
                .title(roadmapSharingRequestDto.getTitle())
                .roadmapType(roadmapType)
                .introduction(roadmapSharingRequestDto.getIntroduction())
                .user(user)
                .build();
        user.addRoadmap(roadmap);
        return roadmap;
    }

}
