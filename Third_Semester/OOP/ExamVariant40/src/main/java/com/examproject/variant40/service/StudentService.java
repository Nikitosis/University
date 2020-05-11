package com.examproject.variant40.service;

import com.examproject.variant40.aggregation.input.*;
import com.examproject.variant40.entity.*;
import com.examproject.variant40.exception.NotFoundException;
import com.examproject.variant40.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SportsFieldService sportsFieldService;



    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found by id"));
    }

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student createStudent(StudentSettings studentSettings) {
        Student student = new Student();
        student.setName(studentSettings.getName());

        Set<StudentFieldScore> studentFieldScores = studentSettings.getFieldScores().stream()
                .map(fieldScoreSettings -> getFieldScore(student, fieldScoreSettings))
                .collect(Collectors.toSet());

        student.setFieldScores(studentFieldScores);

        student.setRandomEnhancement(getRandomEnhancement(student, studentSettings.getRandomEnhancement()));

        Set<HatedStudentsEnhancement> hatedStudentsEnhancements = studentSettings.getHatedStudentsEnhancements().stream()
                .map(hatedStudentsEnhancementSettings -> getHatedSturendEnhancement(student, hatedStudentsEnhancementSettings))
                .collect(Collectors.toSet());

        student.setHatedStudentsEnhancements(hatedStudentsEnhancements);

        student.setPreviousCompetitionEnhancement(getPreviousCompetitionEnhancement(student, studentSettings.getPreviousCompetitionEnhancement()));

        return createStudent(student);
    }

    private StudentFieldScore getFieldScore(Student student, FieldScoreSettings fieldScoreSettings) {
        if(fieldScoreSettings == null) {
            return null;
        }
        StudentFieldScore studentFieldScore = new StudentFieldScore();
        studentFieldScore.setSportsField(sportsFieldService.getByName(fieldScoreSettings.getFieldName()));
        studentFieldScore.setScore(fieldScoreSettings.getScore().intValue());
        studentFieldScore.setStudent(student);
        return studentFieldScore;
    }

    private RandomEnhancement getRandomEnhancement(Student student, RandomEnhancementSettings randomEnhancementSettings) {
        if(randomEnhancementSettings == null) {
            return null;
        }
        RandomEnhancement randomEnhancement = new RandomEnhancement();
        randomEnhancement.setMinCoefficient(randomEnhancementSettings.getMinCoefficient());
        randomEnhancement.setMaxCoefficient(randomEnhancementSettings.getMaxCoefficient());
        randomEnhancement.setStudent(student);
        return randomEnhancement;
    }

    private PreviousCompetitionEnhancement getPreviousCompetitionEnhancement(Student student, PreviousCompetitionEnhancementSettings previousCompetitionEnhancementSettings) {
        if(previousCompetitionEnhancementSettings == null) {
            return null;
        }

        PreviousCompetitionEnhancement previousCompetitionEnhancement = new PreviousCompetitionEnhancement();
        previousCompetitionEnhancement.setCoefficient(previousCompetitionEnhancementSettings.getCoefficient());
        previousCompetitionEnhancement.setStudent(student);
        return previousCompetitionEnhancement;
    }

    private HatedStudentsEnhancement getHatedSturendEnhancement(Student student, HatedStudentsEnhancementSettings hatedStudentsEnhancementSettings) {
        if(hatedStudentsEnhancementSettings == null) {
            return null;
        }
        HatedStudentsEnhancement hatedStudentsEnhancement = new HatedStudentsEnhancement();
        hatedStudentsEnhancement.setHatedStudent(getById(hatedStudentsEnhancementSettings.getHatedStudentId()));
        hatedStudentsEnhancement.setCoefficient(hatedStudentsEnhancementSettings.getCoefficient());
        hatedStudentsEnhancement.setStudent(student);
        return hatedStudentsEnhancement;
    }
}
