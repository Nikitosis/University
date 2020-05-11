package com.examproject.variant40.repository;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.CompetitionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompetitionTypeRepository extends JpaRepository<CompetitionType, Long> {
    Optional<CompetitionType> findByName(String name);
}
