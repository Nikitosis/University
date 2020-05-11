package com.examproject.variant40.repository;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT c.students FROM Competition c WHERE c = :competition")
    List<Student> findByCompetition(@Param("competition") Competition competition);
}
