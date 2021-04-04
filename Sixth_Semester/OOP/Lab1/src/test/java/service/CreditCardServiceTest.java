package service;

import entities.dao.BankAccount;
import entities.dao.CreditCard;
import entities.request.CreditCardTransferRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import repository.BankAccountRepository;
import repository.CreditCardRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("jdk.internal.reflect.*")
public class CreditCardServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CreditCardRepository creditCardRepository;

    private CreditCardService creditCardService = new CreditCardService();

    @Before
    public void init() {
        Whitebox.setInternalState(creditCardService, "userService", userService);
        Whitebox.setInternalState(creditCardService, "bankAccountRepository", bankAccountRepository);
        Whitebox.setInternalState(creditCardService, "bankAccountService", bankAccountService);
        Whitebox.setInternalState(creditCardService, "creditCardRepository", creditCardRepository);
    }

    @Test
    public void transferMoney() {
        CreditCardTransferRequest request = new CreditCardTransferRequest();
        request.setAmount(BigDecimal.valueOf(10));
        request.setCardFromId(1L);
        request.setCardToId(2L);

        CreditCard card1 = new CreditCard();
        CreditCard card2 = new CreditCard();

        BankAccount bankAccount1 = new BankAccount();
        BankAccount bankAccount2 = new BankAccount();

        when(creditCardRepository.findById(request.getCardFromId())).thenReturn(Optional.of(card1));
        when(creditCardRepository.findById(request.getCardToId())).thenReturn(Optional.of(card2));

        when(bankAccountService.getByCardId(request.getCardFromId())).thenReturn(bankAccount1);
        when(bankAccountService.getByCardId(request.getCardToId())).thenReturn(bankAccount2);

        creditCardService.transferMoney(request);

        verify(bankAccountService).transfer(bankAccount1.getId(), bankAccount2.getId(), request.getAmount());
    }
}
