package com.examproject.variant40.aggregation.output;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDTO {
    private Long id;
    private String name;
    private Set<StudentFieldScoreDTO> fieldScores;
    private Set<CompetitionDTO> competitions;
    private Set<CompetitionResultsDTO> competitionResults;
}
