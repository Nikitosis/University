package service;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import entities.dao.CreditCard;
import entities.dao.User;
import entities.request.CreditCardBlockRequest;
import entities.request.CreditCardCreateRequest;
import entities.request.CreditCardTransferRequest;
import repository.BankAccountRepository;
import repository.ConnectionFactory;
import repository.ConnectionPool;
import repository.CreditCardRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class CreditCardService {
    public static CreditCardService INSTANCE = new CreditCardService();

    private UserService userService = UserService.INSTANCE;
    private BankAccountRepository bankAccountRepository = BankAccountRepository.INSTANCE;
    private BankAccountService bankAccountService = BankAccountService.INSTANCE;
    private CreditCardRepository creditCardRepository = CreditCardRepository.INSTANCE;

    public CreditCard create(Long userId, CreditCardCreateRequest request) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_READ_COMMITTED);

            User user = userService.getById(userId);

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
            ConnectionPool.INSTANCE.releaseConnection(connection);
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

    public void block(Long cardId) {
        CreditCard card = getById(cardId);
        BankAccount bankAccount = bankAccountService.getByCardId(card.getId());

        bankAccountService.block(bankAccount);
    }

    public void unblock(Long cardId) {
        CreditCard card = getById(cardId);
        BankAccount bankAccount = bankAccountService.getByCardId(card.getId());

        bankAccountService.unblock(bankAccount);
    }

    public void topUp(Long cardId, BigDecimal amount) {
        BankAccount bankAccount = bankAccountService.getByCardId(cardId);

        bankAccountService.topUp(bankAccount.getId(), amount);
    }
}
