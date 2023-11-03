package com.kusitms.mainservice.domain.user.repository;

import com.kusitms.mainservice.domain.user.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsTeamByTitle(String title);
}
