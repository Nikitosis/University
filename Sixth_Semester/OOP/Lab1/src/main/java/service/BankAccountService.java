package service;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import entities.dao.Transaction;
import repository.BankAccountRepository;
import repository.ConnectionFactory;
import repository.ConnectionPool;
import repository.TransactionRepository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;

public class BankAccountService {
    public static BankAccountService INSTANCE = new BankAccountService();

    private static BankAccountRepository bankAccountRepository = BankAccountRepository.INSTANCE;
    private static TransactionRepository transactionRepository = TransactionRepository.INSTANCE;

    private BankAccountService() {}

    public BankAccount getById(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find BankAccount by id=%d", id)));
    }

    public BankAccount getByCardId(Long cardId) {
        return bankAccountRepository.findByCardId(cardId)
                .orElseThrow(() -> new RuntimeException(String.format("Can't find BankAccount by card id=%d", cardId)));
    }

    public BankAccount block(BankAccount bankAccount) {
        bankAccount.setStatus(BankAccountStatus.BLOCKED);
        return bankAccountRepository.update(bankAccount);
    }

    public BankAccount unblock(BankAccount bankAccount) {
        bankAccount.setStatus(BankAccountStatus.ACTIVE);
        return bankAccountRepository.update(bankAccount);
    }

    public void topUp(Long bankAccountId, BigDecimal amount) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            ConnectionFactory.beginTransaction(connection, Connection.TRANSACTION_REPEATABLE_READ);
            BankAccount bankAccount = bankAccountRepository.findById(bankAccountId, connection).get();

            if(!bankAccount.getStatus().equals(BankAccountStatus.ACTIVE)) {
                throw new RuntimeException("Bank account from status doesn't allow top up");
            }

            bankAccount.setBalance(bankAccount.getBalance().add(amount));

            bankAccountRepository.update(bankAccount, connection);

            ConnectionFactory.commitTransaction(connection);

        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public void transfer(Long bankAccountFromId, Long bankAccountToId, BigDecimal amount) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
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

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setFromAccount(bankAccountFrom);
            transaction.setToAccount(bankAccountTo);
            transaction.setCreatedAt(LocalDateTime.now());
            transactionRepository.create(transaction, connection);

            ConnectionFactory.commitTransaction(connection);

        } catch (Exception e) {
            ConnectionFactory.rollbackTransaction(connection);
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
