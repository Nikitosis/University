package com.examproject.variant40.scheduler;

import com.examproject.variant40.entity.*;
import com.examproject.variant40.service.CompetitionResultService;
import com.examproject.variant40.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CompetitionStarter {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private CompetitionResultService competitionResultService;

    @Transactional
    @Scheduled(fixedDelayString = "${competition.delay.milliseconds}")
    public void holdCompetitions() {
        getCompetitionsToHold()
                .stream()
                .forEach(competition -> holdCompetition(competition));
    }

    private List<Competition> getCompetitionsToHold() {
        return competitionService.getCompetitionsToHold();
    }

    private void holdCompetition(Competition competition) {
        competition.setFinished(true);

        for(Student participant : competition.getStudents()) {
            CompetitionResults competitionResults = new CompetitionResults();
            competitionResults.setStudent(participant);
            competitionResults.setCompetition(competition);
            competitionResults.setTotalScore(calculateStudentScore(competition, participant));

            competition.getCompetitionResults().add(competitionResults);
        }

        competitionService.save(competition);
    }

    private Integer calculateStudentScore(Competition competition, Student student) {
        Integer finalScore = 0;
        for(StudentFieldScore fieldScore : student.getFieldScores()) {
            if(competition.getSportsFields().contains(fieldScore.getSportsField())) {
                finalScore += fieldScore.getScore();
            }
        }

        if(student.getRandomEnhancement() != null) {
            BigDecimal min = student.getRandomEnhancement().getMinCoefficient();
            BigDecimal max = student.getRandomEnhancement().getMaxCoefficient();
            BigDecimal resultCoef = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
            finalScore = resultCoef.multiply(new BigDecimal(finalScore)).intValue();
        }

        if(student.getPreviousCompetitionEnhancement() != null) {
            for(CompetitionResults competitionResults: student.getCompetitionResults()) {
                CompetitionType previousCompType = competitionResults.getCompetition().getCompetitionType();
                CompetitionType curCompType = competition.getCompetitionType();
                if(Objects.equals(previousCompType, curCompType)) {
                    finalScore = student.getPreviousCompetitionEnhancement().getCoefficient().multiply(new BigDecimal(finalScore)).intValue();
                }
            }
        }

        if(!student.getHatedStudentsEnhancements().isEmpty()) {
            for(HatedStudentsEnhancement hatedStudentsEnhancement : student.getHatedStudentsEnhancements()) {
                Student hatedStudent = hatedStudentsEnhancement.getHatedStudent();
                if(competition.getStudents().contains(hatedStudent)) {
                    finalScore = hatedStudentsEnhancement.getCoefficient().multiply(new BigDecimal(finalScore)).intValue();
                }
            }
        }

        return finalScore;
    }
}
