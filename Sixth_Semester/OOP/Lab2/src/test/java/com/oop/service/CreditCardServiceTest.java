package com.oop.service;

import com.oop.entities.dao.BankAccount;
import com.oop.entities.dao.CreditCard;
import com.oop.entities.dao.User;
import com.oop.entities.request.CreditCardCreateRequest;
import com.oop.entities.request.CreditCardTransferRequest;
import com.oop.repository.CreditCardRepository;
import com.oop.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreditCardServiceTest {
    @Mock
    private UserService userService;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CreditCardRepository creditCardRepository;

    @InjectMocks
    private CreditCardService creditCardService;

    @Test
    void create() {
        CreditCardCreateRequest request = TestData.getCreditCardCreateRequest();
        User user = new User();
        user.setId(22L);

        BankAccount bankAccount = new BankAccount();

        when(userService.getById(user.getId())).thenReturn(user);
        when(bankAccountService.create(user)).thenReturn(bankAccount);

        creditCardService.create(user.getId(), request);

        ArgumentCaptor<CreditCard> creditCardArgumentCaptor = ArgumentCaptor.forClass(CreditCard.class);
        verify(creditCardRepository).save(creditCardArgumentCaptor.capture());

        CreditCard creditCard = creditCardArgumentCaptor.getValue();

        assertEquals(request.getName(), creditCard.getName());
        assertEquals(bankAccount, creditCard.getBankAccount());
    }

    @Test
    void transferMoney() {
        CreditCard card1 = new CreditCard();
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(22L);
        card1.setBankAccount(bankAccount1);

        CreditCard card2 = new CreditCard();
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(23L);
        card2.setBankAccount(bankAccount2);

        CreditCardTransferRequest request = new CreditCardTransferRequest();
        request.setAmount(BigDecimal.valueOf(12));
        request.setCardFromId(1L);
        request.setCardToId(2L);

        when(creditCardRepository.findById(request.getCardFromId())).thenReturn(Optional.of(card1));
        when(creditCardRepository.findById(request.getCardToId())).thenReturn(Optional.of(card2));

        creditCardService.transferMoney(request);

        verify(bankAccountService).transfer(bankAccount1.getId(), bankAccount2.getId(), request.getAmount());
    }
}
