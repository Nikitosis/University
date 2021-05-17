package com.oop.service;

import com.oop.entities.dao.BankAccount;
import com.oop.entities.dao.Transaction;
import com.oop.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Transaction create(BigDecimal amount, BankAccount bankAccountFrom, BankAccount bankAccountTo) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setFromAccount(bankAccountFrom);
        transaction.setToAccount(bankAccountTo);

        return transactionRepository.save(transaction);
    }
}
