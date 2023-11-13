package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.user.domain.User;
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
public class Roadmap {
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
}
