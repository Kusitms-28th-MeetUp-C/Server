package com.kusitms.mainservice.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "team_space")
@Entity
public class TeamSpace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_space_id")
    private Long id;
    private String url;
    @Enumerated(EnumType.STRING)
    private TeamSpaceType teamSpaceType;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public static TeamSpace createTeamSpace(String url, TeamSpaceType teamSpaceType, Team team){
        TeamSpace teamSpace = TeamSpace.builder()
                .url(url)
                .teamSpaceType(teamSpaceType)
                .build();
        team.addTeamSpaceList(teamSpace);
        return teamSpace;
    }
}
