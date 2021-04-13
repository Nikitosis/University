package com.oop.entities.response;

import com.oop.entities.dao.BankAccountStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountResponse {
    private Long id;
    private BigDecimal balance;
    private BankAccountStatus status;
}
