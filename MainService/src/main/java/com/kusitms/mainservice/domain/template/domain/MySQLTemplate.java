package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "template")
@Entity
public class MySQLTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;


}

