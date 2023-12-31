package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "reviewer")
@Entity
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewer_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private TemplateReview templateReview;
    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    public static Reviewer createReviewer(User user, TemplateReview templateReview, Template template) {
        Reviewer reviewer = Reviewer.builder()
                .user(user)
                .templateReview(templateReview)
                .template(template)
                .build();
        user.addReviewer(reviewer);
        templateReview.addReviewer(reviewer);
        template.addReviewer(reviewer);
        return reviewer;
    }
}
