package com.kusitms.mainservice.domain.template.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "custom_template")
@Entity
public class CustomTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "custom_tamplate_id")
    private Long id;
    private String title;
}
