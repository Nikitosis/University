package com.examproject.variant40.controller;

import com.examproject.variant40.aggregation.input.CompetitionSettings;
import com.examproject.variant40.aggregation.output.CompetitionDTO;
import com.examproject.variant40.aggregation.output.CompetitionDetailsDTO;
import com.examproject.variant40.entity.Competition;
import com.examproject.variant40.service.CompetitionService;
import com.examproject.variant40.utils.ObjectMapperUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    private CompetitionService competitionService;
    @Autowired
    private ObjectMapperUtils objectMapperUtils;

    @GetMapping
    public List<CompetitionDTO> getAll(  @RequestParam(value = "sportsFields", required = false) List<String> sportsFields,
                                         @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
                                         @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate
                                        ) {
        return objectMapperUtils.mapAll(competitionService.getAll(sportsFields, startDate, endDate),CompetitionDTO.class);
    }

    @GetMapping("/{competitionId}")
    public CompetitionDetailsDTO getCompetition(@PathVariable("competitionId") Long competitionId) {
        return objectMapperUtils.map(competitionService.getById(competitionId),CompetitionDetailsDTO.class);
    }

    @PostMapping
    public CompetitionDetailsDTO createCompetition(@RequestBody CompetitionSettings competitionSettings) {
        return objectMapperUtils.map(competitionService.save(competitionSettings),CompetitionDetailsDTO.class);
    }

    @PutMapping("/{competitionId}/participant/{participantId}")
    public CompetitionDetailsDTO checkInParticipant(@PathVariable("competitionId") Long competitionId,
                                                    @PathVariable("participantId") Long participantId) {
        Competition competition = competitionService.checkInParticipant(competitionId, participantId);
        return objectMapperUtils.map(competition,CompetitionDetailsDTO.class);
    }
}
