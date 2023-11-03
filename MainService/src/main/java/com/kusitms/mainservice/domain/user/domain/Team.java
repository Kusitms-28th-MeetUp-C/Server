package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "team")
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private TeamSpace teamSpace;
    private String introduction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private RoadmapDownload roadmapDownload;
}
