package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "roadmap_template")
@Entity
public class RoadmapTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_template_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "roadmap_datail_id")
    private RoadmapDetail roadmapDetail;
    @ManyToOne
    @JoinColumn(name = "template_download_id")
    private TemplateDownload templateDownload;
}
