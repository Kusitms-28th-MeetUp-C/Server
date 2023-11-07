package com.kusitms.mainservice.domain.team.repository;

import com.kusitms.mainservice.domain.team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsTeamByTitle(String title);

    List<Team> findAllByUserId(Long userId);
}
