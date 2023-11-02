package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;


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
    @ManyToOne
    @JoinColumn(name = "maker_id")
    private User user;

}

