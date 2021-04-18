package repository;

import entities.dao.BankAccount;
import entities.dao.CreditCard;
import mappers.BankAccountMapper;
import mappers.CreditCardMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class BankAccountRepository {
    public static BankAccountRepository INSTANCE = new BankAccountRepository();

    private BankAccountRepository() {}

    public Optional<BankAccount> findById(Long id, Connection connection) {
        try {
            String command = "SELECT * FROM bank_account WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(BankAccountMapper.INSTANCE.resultSetToEntity(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Optional<BankAccount> findById(Long id) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
           return findById(id, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<BankAccount> findByCardId(Long id) {
        String command = "SELECT ba.* FROM bank_account ba INNER JOIN credit_card cc ON cc.bank_account_id = ba.id WHERE cc.id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(BankAccountMapper.INSTANCE.resultSetToEntity(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public BankAccount update(BankAccount bankAccount, Connection connection) {
        try {
            String command = "UPDATE bank_account SET balance = ?, status = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setBigDecimal(1, bankAccount.getBalance());
            preparedStatement.setString(2, bankAccount.getStatus().toString());
            preparedStatement.setLong(3, bankAccount.getId());

            preparedStatement.executeUpdate();

            return bankAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BankAccount update(BankAccount bankAccount) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            return update(bankAccount, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public BankAccount create(BankAccount bankAccount, Connection connection) {
        try {
            String command = "INSERT INTO bank_account (user_id, balance, status) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, bankAccount.getUser().getId());
            preparedStatement.setBigDecimal(2, bankAccount.getBalance());
            preparedStatement.setString(3, bankAccount.getStatus().toString());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                bankAccount.setId(generatedKeys.getLong(1));
            }

            return bankAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public BankAccount create(BankAccount bankAccount) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
           return create(bankAccount, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
