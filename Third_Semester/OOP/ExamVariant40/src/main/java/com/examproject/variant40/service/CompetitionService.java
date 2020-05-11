package com.examproject.variant40.service;

import com.examproject.variant40.aggregation.input.CompetitionSettings;
import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.entity.SportsField;
import com.examproject.variant40.entity.Student;
import com.examproject.variant40.exception.CompetitionHasFinishedException;
import com.examproject.variant40.exception.NotFoundException;
import com.examproject.variant40.exception.WrongDataException;
import com.examproject.variant40.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompetitionService {
    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CompetitionTypeService competitionTypeService;
    @Autowired
    private SportsFieldService sportsFieldService;

    public Competition getById(Long id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book not found by id"));
    }

    public List<Competition> getAll() {
        return competitionRepository.findAll();
    }

    public List<Competition> getAll(List<String> sportsFieldsStr, LocalDateTime startDate, LocalDateTime endDate) {
        if(sportsFieldsStr != null && startDate != null && endDate != null) {
            List<SportsField> sportsFields = sportsFieldsStr.stream()
                    .map(fieldName -> sportsFieldService.getByName(fieldName))
                    .collect(Collectors.toList());

            return competitionRepository.findAll(sportsFields,startDate, endDate);
        }

        if(sportsFieldsStr != null) {
            List<SportsField> sportsFields = sportsFieldsStr.stream()
                    .map(fieldName -> sportsFieldService.getByName(fieldName))
                    .collect(Collectors.toList());

            return competitionRepository.findAll(sportsFields);
        }

        if(startDate != null && endDate != null) {
            return competitionRepository.findAll(startDate, endDate);
        }

        if(sportsFieldsStr == null && startDate == null && endDate == null) {
            return competitionRepository.findAll();
        }

        throw new WrongDataException("Wrong data provided");
    }
    public Competition save(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Transactional
    public Competition save(CompetitionSettings competitionSettings) {
        Set<SportsField> sportsFields = competitionSettings.getSportFields().stream()
                .map(fieldName -> sportsFieldService.getByName(fieldName))
                .collect(Collectors.toSet());

        Competition competition = new Competition();
        competition.setName(competitionSettings.getName());
        competition.setDate(competitionSettings.getDate());
        competition.setCompetitionType(competitionTypeService.getByName(competitionSettings.getCompetitionType()));
        competition.setSportsFields(sportsFields);
        competition.setFinished(false);

        return save(competition);
    }

    @Transactional
    public Competition checkInParticipant(Long competitionId, Long participantId) {
        Competition competition = competitionRepository.findById(competitionId).get();
        Student student = studentService.getById(participantId);

        if(competition.getFinished()) {
            throw new CompetitionHasFinishedException("Can't check in, because competition has already finished");
        }

        competition.getStudents().add(student);
        return competitionRepository.save(competition);
    }

    public List<Competition> getCompetitionsToHold() {
        return competitionRepository.getCompetitionsToHold();
    }

}
