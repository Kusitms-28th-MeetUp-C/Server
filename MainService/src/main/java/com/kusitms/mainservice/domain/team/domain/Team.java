package com.kusitms.mainservice.domain.team.domain;

import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import com.kusitms.mainservice.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.kusitms.mainservice.domain.team.domain.TeamType.getEnumTeamTypeFromStringTeamType;

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
    @ManyToOne
    @JoinColumn(name = "roadmap_download_id")
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

    public void addRoadmapDownload(RoadmapDownload roadmapDownload) {
        this.roadmapDownload = roadmapDownload;
        roadmapDownload.addTeam(this);
    }

    public void addTeamSpaceList(TeamSpace teamSpace) {
        this.teamSpaceList.add(teamSpace);
    }

    public void updateTeamInfo(String title, String teamType, String introduction) {
        this.title = Objects.isNull(title) ? this.title : title;
        this.teamType = Objects.isNull(teamType) ? this.teamType : getEnumTeamTypeFromStringTeamType(teamType);
        this.introduction = Objects.isNull(introduction) ? this.introduction : introduction;
    }

    public void resetTeamSpaceList() {
        this.teamSpaceList = new ArrayList<>();
    }
}
