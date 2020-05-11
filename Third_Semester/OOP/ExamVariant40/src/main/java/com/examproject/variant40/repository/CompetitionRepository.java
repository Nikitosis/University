package com.examproject.variant40.repository;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.SportsField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition,Long> {
    @Query("FROM Competition c WHERE c.finished = FALSE AND c.date <= current_timestamp()")
    List<Competition> getCompetitionsToHold();

    @Query("SELECT DISTINCT c FROM Competition c INNER JOIN c.sportsFields sf WHERE c.date >= :startDate AND c.date <= :endDate AND sf in (:sportsFields) ")
    List<Competition> findAll(@Param("sportsFields") List<SportsField> sportsFields,
                              @Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    @Query("FROM Competition c WHERE c.date >= :startDate AND c.date <= :endDate")
    List<Competition> findAll(@Param("startDate") LocalDateTime startDate,
                              @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DISTINCT c FROM Competition c INNER JOIN c.sportsFields sf WHERE sf in (:sportsFields) ")
    List<Competition> findAll(@Param("sportsFields") List<SportsField> sportsFields);
}
