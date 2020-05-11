package com.examproject.variant40.aggregation.output;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentFieldScoreDTO {
    private Long id;
    private StudentDTO student;
    private SportsFieldDTO sportsField;
    private Integer score;
}
