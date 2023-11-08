package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "template")
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private TemplateType templateType;
    @Builder.Default
    private int count = 0;
    private int estimatedTime;
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User user;
    @OneToMany(mappedBy = "template")
    @Builder.Default
    private List<Reviewer> reviewerList = new ArrayList<>();
    @OneToMany(mappedBy = "template")
    @Builder.Default
    private List<TemplateDownload> templateDownloadList = new ArrayList<>();
    @OneToMany(mappedBy = "template")
    @Builder.Default
    private List<RoadmapTemplate> roadmapTemplates = new ArrayList<>();
}

