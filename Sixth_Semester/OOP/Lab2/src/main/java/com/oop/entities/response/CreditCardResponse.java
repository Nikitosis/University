package com.oop.entities.response;

import lombok.Data;

@Data
public class CreditCardResponse {
    private Long id;
    private String name;
    private BankAccountResponse bankAccount;
}
