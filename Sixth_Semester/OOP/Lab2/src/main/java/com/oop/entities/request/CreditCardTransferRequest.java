package com.oop.entities.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreditCardTransferRequest {
    private Long cardFromId;
    private Long cardToId;
    private BigDecimal amount;
}
