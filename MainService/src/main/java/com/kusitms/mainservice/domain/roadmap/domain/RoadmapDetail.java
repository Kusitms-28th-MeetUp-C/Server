package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "roadmap_detail")
@Entity
public class RoadmapDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_detail_id")
    private Long id;
    private String title;
    private int step;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;
    @OneToMany(mappedBy = "RoadmapDetail")
    @Builder.Default
    private List<TemplateDownload> templateDownloadList = new ArrayList<>();
}
