package com.kusitms.mainservice.domain.template.domain;

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
public class TemplateReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template_review_id")
    private Long id;
    private String content;
    @OneToOne
    private Reviewer reviewer;

    public static TemplateReview createTemplateReview(String content){
        return TemplateReview.builder()
                .content(content)
                .build();
    }

    public void addReviewer(Reviewer reviewer){
        this.reviewer = reviewer;
    }
}
