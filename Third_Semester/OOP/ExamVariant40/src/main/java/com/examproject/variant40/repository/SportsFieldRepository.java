package com.examproject.variant40.repository;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.SportsField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SportsFieldRepository extends JpaRepository<SportsField, Long> {
    Optional<SportsField> findByName(String name);

    @Query("SELECT c.sportsFields FROM Competition c WHERE c = :competition")
    List<SportsField> findByCompetition(@Param("competition")Competition competition);
}
