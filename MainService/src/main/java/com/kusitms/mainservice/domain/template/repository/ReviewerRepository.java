package com.kusitms.mainservice.domain.template.repository;

import com.kusitms.mainservice.domain.template.domain.Reviewer;
import com.kusitms.mainservice.domain.template.domain.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    List<Reviewer> findByTemplate(Template template);
}
