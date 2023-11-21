package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapTemplate;
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
@Table(name = "template")
@Entity
@Setter
public class Template extends BaseTimeEntity {
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
    private String introduction;
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

    public static Template createTemplate(TemplateSharingRequestDto templateSharingRequestDto, TemplateType templateType, User user) {
        Template template = Template.builder()
                .title(templateSharingRequestDto.getTitle())
                .templateType(templateType)
                .estimatedTime(templateSharingRequestDto.getEstimatedTime())
                .introduction(templateSharingRequestDto.getIntroduction())
                .user(user)
                .build();
        user.addTemplate(template);
        return template;
    }

    public void addReviewer(Reviewer reviewer) {
        this.reviewerList.add(reviewer);
    }
}

