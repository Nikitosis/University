package com.examproject.variant40.aggregation.output;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionDTO {
    private Long id;
    private String name;
    private Boolean finished;
    private LocalDateTime date;
    private CompetitionTypeDTO competitionType;
}
