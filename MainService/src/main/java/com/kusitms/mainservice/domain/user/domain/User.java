package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.domain.roadmap.domain.Roadmap;
import com.kusitms.mainservice.domain.roadmap.domain.RoadmapDownload;
import com.kusitms.mainservice.domain.team.domain.Team;
import com.kusitms.mainservice.domain.template.domain.Reviewer;
import com.kusitms.mainservice.domain.template.domain.Template;
import com.kusitms.mainservice.domain.template.domain.TemplateDownload;
import com.kusitms.mainservice.domain.user.auth.PlatformUserInfo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "user")
@Entity
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private UserType userType;
    private String platformId;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Team> teamList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Roadmap> roadmapList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<RoadmapDownload> roadmapDownloadList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<TemplateDownload> templateDownloadList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Template> templateList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Reviewer> reviewerList = new ArrayList<>();

    public static User createUser(PlatformUserInfo platformUserInfo, Platform platform) {
        return User.builder()
                .platform(platform)
                .platformId(platformUserInfo.getId())
                .email(platformUserInfo.getEmail())
                .name(platformUserInfo.getName())
                .profile(platformUserInfo.getPicture())
                .build();
    }

    public void addTemplate(Template template){
        this.templateList.add(template);
    }

    public void addRoadmapDownload(RoadmapDownload roadmapDownload){
        this.roadmapDownloadList.add(roadmapDownload);
    }

    public void addReviewer(Reviewer reviewer){
        this.reviewerList.add(reviewer);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateTeamList(Team team) {
        this.teamList.add(team);
    }
}
