package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewer_id")
    private Long id;

    @ManyToOne
    private User user;
    @ManyToOne
    private TemplateReview templateReview;
    @ManyToOne
    private TemplateMaker templateMaker;
}
