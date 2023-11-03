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
@Table(name = "template_download")
@Entity
public class TemplateDownload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_download_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;
    @OneToOne
    private CustomTemplate customTemplate;
    @OneToMany(mappedBy = "templateDownload")
    @Builder.Default
    private List<RoadmapTemplate> roadmapTemplateList = new ArrayList<>();
}
