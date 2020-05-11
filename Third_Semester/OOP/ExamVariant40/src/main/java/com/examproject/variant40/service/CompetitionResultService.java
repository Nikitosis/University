package com.examproject.variant40.service;

import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.CompetitionResults;
import com.examproject.variant40.entity.Student;
import com.examproject.variant40.exception.NotFoundException;
import com.examproject.variant40.repository.CompetitionResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetitionResultService {
    @Autowired
    private CompetitionResultsRepository competitionResultsRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CompetitionService competitionService;

    public CompetitionResults findByStudentId(Long studentId) {
        Student student = studentService.getById(studentId);
        return competitionResultsRepository.findAllByStudent(student);
    }

    public CompetitionResults findByCompetitionId(Long competitionId) {
        Competition competition = competitionService.getById(competitionId);
        return competitionResultsRepository.findAllByCompetition(competition);
    }

    public CompetitionResults findByCompetitionIdAndStudentId(Long studentId, Long competitionId) {
        Student student = studentService.getById(studentId);
        Competition competition = competitionService.getById(competitionId);
        return competitionResultsRepository.findByCompetitionAndStudent(competition, student);
    }
}
