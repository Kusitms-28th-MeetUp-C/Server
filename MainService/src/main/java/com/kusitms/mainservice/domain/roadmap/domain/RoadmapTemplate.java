package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.template.domain.Template;
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
    @JoinColumn(name = "roadmap_detail_id")
    private RoadmapSpace roadmapSpace;
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;
}
