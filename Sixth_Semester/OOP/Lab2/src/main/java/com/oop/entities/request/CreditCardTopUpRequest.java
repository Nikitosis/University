package com.oop.entities.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardTopUpRequest {
    private Long cardId;
    private BigDecimal amount;
}
