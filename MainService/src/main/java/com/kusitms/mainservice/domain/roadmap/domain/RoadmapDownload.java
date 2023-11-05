package com.kusitms.mainservice.domain.roadmap.domain;

import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "roadmap_download")
@Entity
public class RoadmapDownload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_download_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "roadmap_id")
    private Roadmap roadmap;
    @OneToOne
    private CustomRoadmap customRoadmap;
    @OneToMany(mappedBy = "roadmapDownload")
    @Builder.Default
    private List<Team> teamList = new ArrayList<>();

    public void addTeam(Team team){
        this.teamList.add(team);
    }
}
