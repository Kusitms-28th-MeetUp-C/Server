package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String title;
    @Enumerated(EnumType.STRING)
    private TeamType teamType;
    private String introduction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private RoadmapDownload roadmapDownload;
    @OneToMany(mappedBy = "team")
    @Builder.Default
    private List<TeamSpace> teamSpaceList = new ArrayList<>();

    public static Team createTeam(String title, TeamType teamType, String introduction, User user) {
        Team team = Team.builder()
                .title(title)
                .teamType(teamType)
                .introduction(introduction)
                .user(user)
                .build();
        user.updateTeamList(team);
        return team;
    }

    public void addTeamSpaceList(TeamSpace teamSpace){
        this.teamSpaceList.add(teamSpace);
    }
}
