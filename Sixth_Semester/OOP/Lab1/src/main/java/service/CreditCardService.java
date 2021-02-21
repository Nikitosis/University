package service;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import entities.dao.CreditCard;
import entities.dao.User;
import entities.request.CreditCardCreateRequest;
import entities.request.CreditCardTransferRequest;
import repository.BankAccountRepository;
import repository.ConnectionFactory;
import repository.CreditCardRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class CreditCardService {
    public static CreditCardService INSTANCE = new CreditCardService();

    private static UserService userService = UserService.INSTANCE;
    private static BankAccountRepository bankAccountRepository = BankAccountRepository.INSTANCE;
    private static BankAccountService bankAccountService = BankAccountService.INSTANCE;
    private static CreditCardRepository creditCardRepository = CreditCardRepository.INSTANCE;

    private CreditCardService() {}

    public CreditCard create(CreditCardCreateRequest request) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

            User user = userService.getById(request.getUserId());

            BankAccount bankAccount = new BankAccount();
            bankAccount.setBalance(BigDecimal.ZERO);
            bankAccount.setStatus(BankAccountStatus.ACTIVE);
            bankAccount.setUser(user);

            bankAccountRepository.create(bankAccount);

            CreditCard creditCard = new CreditCard();
            creditCard.setName(request.getName());
            creditCard.setBankAccount(bankAccount);

            creditCardRepository.create(creditCard);

            ConnectionFactory.commitTransaction(connection);

            return creditCard;
        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(connection);
        }
    }

    public List<CreditCard> getByUserId(Long userId) {
        return creditCardRepository.findByUserId(userId);
    }

    public CreditCard getById(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find CreditCard by id=%d", id)));
    }

    public void transferMoney(CreditCardTransferRequest request) {
        CreditCard cardFrom = getById(request.getCardFromId());
        CreditCard cardTo = getById(request.getCardToId());

        BankAccount bankAccountFrom = bankAccountService.getByCardId(request.getCardFromId());
        BankAccount bankAccountTo = bankAccountService.getByCardId(request.getCardToId());

        bankAccountService.transfer(bankAccountFrom.getId(), bankAccountTo.getId(), request.getAmount());
    }
}
