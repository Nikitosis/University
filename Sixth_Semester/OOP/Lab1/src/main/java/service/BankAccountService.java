package service;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import repository.BankAccountRepository;
import repository.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;

public class BankAccountService {
    public static BankAccountService INSTANCE = new BankAccountService();

    private static BankAccountRepository bankAccountRepository = BankAccountRepository.INSTANCE;

    private BankAccountService() {}

    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find BankAccount by id=%d", id)));
    }

    public BankAccount getByCardId(Long cardId) {
        return bankAccountRepository.findByCardId(cardId)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find BankAccount by card id=%d", cardId)));
    }

    public void transfer(Long bankAccountFromId, Long bankAccountToId, BigDecimal amount) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_REPEATABLE_READ);
            BankAccount bankAccountFrom = bankAccountRepository.findById(bankAccountFromId, connection).get();
            BankAccount bankAccountTo = bankAccountRepository.findById(bankAccountToId, connection).get();

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

            bankAccountRepository.update(bankAccountFrom, connection);
            bankAccountRepository.update(bankAccountTo, connection);

            ConnectionFactory.commitTransaction(connection);

        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.close(connection);
        }
    }
}
