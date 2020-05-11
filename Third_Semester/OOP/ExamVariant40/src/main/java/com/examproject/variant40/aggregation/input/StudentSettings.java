package com.examproject.variant40.aggregation.input;

import com.examproject.variant40.controller.StudentController;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentSettings {
    private String name;
    private List<FieldScoreSettings> fieldScores = new ArrayList<>();
    private List<HatedStudentsEnhancementSettings> hatedStudentsEnhancements = new ArrayList<>();
    private RandomEnhancementSettings randomEnhancement;
    private PreviousCompetitionEnhancementSettings previousCompetitionEnhancement;

}
