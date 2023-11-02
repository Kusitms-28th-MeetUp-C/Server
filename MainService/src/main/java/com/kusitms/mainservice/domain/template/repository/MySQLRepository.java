package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.MySQLTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

// MySQL Repository
public interface MySQLRepository extends JpaRepository<MySQLTemplate, Long> {
    // MySQL 연산을 추가할 수 있음
}
