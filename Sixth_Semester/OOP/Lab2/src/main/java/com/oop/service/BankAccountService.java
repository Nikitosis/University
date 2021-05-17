package com.oop.service;

import com.oop.entities.dao.BankAccount;
import com.oop.entities.dao.BankAccountStatus;
import com.oop.entities.dao.Transaction;
import com.oop.entities.dao.User;
import com.oop.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionService transactionService;

    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find BankAccount by id=%d", id)));
    }

    public BankAccount block(BankAccount bankAccount) {
        bankAccount.setStatus(BankAccountStatus.BLOCKED);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount unblock(BankAccount bankAccount) {
        bankAccount.setStatus(BankAccountStatus.ACTIVE);
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount create(User user) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.ZERO);
        bankAccount.setStatus(BankAccountStatus.ACTIVE);
        bankAccount.setUser(user);

        return bankAccountRepository.save(bankAccount);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void topUp(BankAccount bankAccount, BigDecimal amount) {

        if(!bankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
            throw new RuntimeException("Bank account from status doesn't allow top up");
        }

        bankAccount.setBalance(bankAccount.getBalance().add(amount));

        bankAccountRepository.save(bankAccount);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(Long bankAccountFromId, Long bankAccountToId, BigDecimal amount) {
        BankAccount bankAccountFrom = getById(bankAccountFromId);
        BankAccount bankAccountTo = getById(bankAccountToId);

        if(bankAccountFrom.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Not enough money");
        }

        if(!bankAccountFrom.getStatus().equals(BankAccountStatus.ACTIVE)) {
            throw new RuntimeException("Bank account from status doesn't allow transfer");
        }

        if(!bankAccountTo.getStatus().equals(BankAccountStatus.ACTIVE)) {
            throw new RuntimeException("Bank account to status doesn't allow transfer");
        }

        bankAccountFrom.setBalance(bankAccountFrom.getBalance().subtract(amount));
        bankAccountTo.setBalance(bankAccountTo.getBalance().add(amount));

        bankAccountRepository.save(bankAccountFrom);
        bankAccountRepository.save(bankAccountTo);

        transactionService.create(amount, bankAccountFrom, bankAccountTo);
    }
}
