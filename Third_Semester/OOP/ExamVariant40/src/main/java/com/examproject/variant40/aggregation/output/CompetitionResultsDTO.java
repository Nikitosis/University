package com.examproject.variant40.aggregation.output;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionResultsDTO {
    private Long id;
    private CompetitionDTO competition;
    private StudentDTO student;
    private Integer totalScore;
}
