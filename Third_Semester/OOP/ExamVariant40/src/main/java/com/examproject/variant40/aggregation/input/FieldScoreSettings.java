package com.examproject.variant40.aggregation.input;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FieldScoreSettings {
    private String fieldName;
    private BigDecimal score;
}
