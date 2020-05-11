package com.examproject.variant40.aggregation.output;


import com.examproject.variant40.entity.CompetitionType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompetitionDetailsDTO {
    private Long id;
    private String name;
    private LocalDateTime date;
    private Boolean finished;
    private CompetitionType competitionType;
    private List<StudentDTO> students;
    private List<SportsFieldDTO> sportsFields;
    private List<CompetitionResultsDTO> competitionResults;
}
