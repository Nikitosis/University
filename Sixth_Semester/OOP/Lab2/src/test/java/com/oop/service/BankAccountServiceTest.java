package com.oop.service;

import com.oop.entities.dao.BankAccount;
import com.oop.entities.dao.BankAccountStatus;
import com.oop.repository.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

//    @Test
//    void transfer() {
//        BankAccount bankAccount1 = new BankAccount();
//        bankAccount1.setId(22L);
//        bankAccount1.setStatus(BankAccountStatus.ACTIVE);
//        bankAccount1.setBalance(BigDecimal.valueOf(100));
//
//        BankAccount bankAccount2 = new BankAccount();
//        bankAccount2.setId(33L);
//        bankAccount2.setStatus(BankAccountStatus.ACTIVE);
//        bankAccount2.setBalance(BigDecimal.valueOf(100));
//
//        BigDecimal amount = BigDecimal.valueOf(22);
//
//        when(bankAccountRepository.findById(bankAccount1.getId())).thenReturn(Optional.of(bankAccount1));
//        when(bankAccountRepository.findById(bankAccount2.getId())).thenReturn(Optional.of(bankAccount2));
//
//        bankAccountService.transfer(bankAccount1.getId(), bankAccount2.getId(), amount);
//
//        verify(bankAccountRepository).save(bankAccount1);
//        verify(bankAccountRepository).save(bankAccount2);
//
//        assertEquals(BigDecimal.valueOf(100).subtract(amount), bankAccount1.getBalance());
//        assertEquals(BigDecimal.valueOf(100).add(amount), bankAccount2.getBalance());
//    }

    @Test
    void transfer_notEnoughMoney() {
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(22L);
        bankAccount1.setStatus(BankAccountStatus.ACTIVE);
        bankAccount1.setBalance(BigDecimal.valueOf(10));

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(33L);
        bankAccount2.setStatus(BankAccountStatus.ACTIVE);
        bankAccount2.setBalance(BigDecimal.valueOf(100));

        BigDecimal amount = BigDecimal.valueOf(22);

        when(bankAccountRepository.findById(bankAccount1.getId())).thenReturn(Optional.of(bankAccount1));
        when(bankAccountRepository.findById(bankAccount2.getId())).thenReturn(Optional.of(bankAccount2));

        assertThrows(RuntimeException.class, () -> bankAccountService.transfer(bankAccount1.getId(), bankAccount2.getId(), amount));
    }

    @Test
    void transfer_notActive() {
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(22L);
        bankAccount1.setStatus(BankAccountStatus.ACTIVE);
        bankAccount1.setBalance(BigDecimal.valueOf(100));

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(33L);
        bankAccount2.setStatus(BankAccountStatus.BLOCKED);
        bankAccount2.setBalance(BigDecimal.valueOf(100));

        BigDecimal amount = BigDecimal.valueOf(22);

        when(bankAccountRepository.findById(bankAccount1.getId())).thenReturn(Optional.of(bankAccount1));
        when(bankAccountRepository.findById(bankAccount2.getId())).thenReturn(Optional.of(bankAccount2));

        assertThrows(RuntimeException.class, () -> bankAccountService.transfer(bankAccount1.getId(), bankAccount2.getId(), amount));
    }
}
