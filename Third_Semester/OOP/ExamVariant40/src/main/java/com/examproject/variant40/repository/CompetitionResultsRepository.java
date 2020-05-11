package com.examproject.variant40.repository;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.CompetitionResults;
import com.examproject.variant40.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionResultsRepository extends JpaRepository<CompetitionResults, Long> {

    CompetitionResults findAllByStudent(Student student);

    CompetitionResults findAllByCompetition(Competition competition);

    CompetitionResults findByCompetitionAndStudent(Competition competition, Student student);
}
