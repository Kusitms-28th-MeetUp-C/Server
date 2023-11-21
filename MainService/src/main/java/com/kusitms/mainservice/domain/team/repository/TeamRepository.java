package com.kusitms.mainservice.domain.team.repository;

import com.kusitms.mainservice.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByUserIdAndTitle(Long userId, String title);

    boolean existsTeamByTitle(String title);

    List<Team> findAllByUserId(Long userId);

    Optional<Team> findById(Long Id);
    @Query("SELECT cr.title FROM Team t JOIN t.roadmapDownload rd JOIN rd.customRoadmap cr WHERE t.title = :teamTitle")
    Optional<String> findCustomRoadmapTitleByTeamTitle(@Param("teamTitle") String teamTitle);
}
