package com.kusitms.mainservice.domain.template.domain;

import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "template_download")
@Entity
@Setter
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

    public static TemplateDownload createTemplateDownload(User user, Template template){
        return TemplateDownload.builder()
                .user(user)
                .template(template)
                .build();
    }

    public void addCustomTemplate(CustomTemplate customTemplate){
        this.customTemplate = customTemplate;
    }
}

