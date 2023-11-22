package com.kusitms.mainservice.domain.user.domain;

import com.kusitms.mainservice.domain.mypage.dto.resquest.ModifyUserProfileRequestDto;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(unique = true)
    private String platformId;
    private String email;
    private String name;
    private String profile;
    private String refreshToken;
    private String sessionId;
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

    public static User createUser(PlatformUserInfo platformUserInfo, Platform platform, String sessionId) {
        return User.builder()
                .platformId(platformUserInfo.getId())
                .platform(platform)
                .email(platformUserInfo.getEmail())
                .name(platformUserInfo.getName())
                .profile(platformUserInfo.getPicture())
                .sessionId(sessionId)
                .build();
    }

    public void updateSignUpUserInfo(String name, UserType userType){
        this.name = name;
        this.userType = userType;
    }

    public void updatePlatform(Platform platform) {
        this.platform = platform;
    }

    public void addTemplate(Template template) {
        this.templateList.add(template);
    }

    public void addRoadmap(Roadmap roadmap) {
        this.roadmapList.add(roadmap);
    }

    public void addRoadmapDownload(RoadmapDownload roadmapDownload) {
        this.roadmapDownloadList.add(roadmapDownload);
    }

    public void addReviewer(Reviewer reviewer) {
        this.reviewerList.add(reviewer);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateTeamList(Team team) {
        this.teamList.add(team);
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public void updateMypage(ModifyUserProfileRequestDto modifyUserProfileRequestDto) {
        this.profile = modifyUserProfileRequestDto.getProfile();
        this.name = modifyUserProfileRequestDto.getName();
        this.userType = UserType.getEnumUserTypeFromStringUserType(modifyUserProfileRequestDto.getUserType());
    }

    public static User getProfile(User user, String profile) {
        return User.builder()
                .id(user.getId())
                .profile(profile)
                .platform(user.getPlatform())
                .userType(user.getUserType())
                .platformId(user.getPlatformId())
                .email(user.getEmail())
                .name(user.getName())
                .refreshToken(user.getRefreshToken())
                .build();
    }
}

