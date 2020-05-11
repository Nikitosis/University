package com.examproject.variant40.aggregation.input;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RandomEnhancementSettings {
    private BigDecimal minCoefficient;
    private BigDecimal maxCoefficient;
}
