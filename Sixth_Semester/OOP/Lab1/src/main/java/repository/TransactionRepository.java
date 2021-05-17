package repository;

import entities.dao.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class TransactionRepository {
    public static TransactionRepository INSTANCE = new TransactionRepository();

    public Transaction create(Transaction transaction, Connection connection) {
        try {
            String command = "INSERT INTO transaction (from_account, to_account, amount, created_at) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, transaction.getFromAccount().getId());
            preparedStatement.setLong(2, transaction.getToAccount().getId());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(transaction.getCreatedAt()));

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                transaction.setId(generatedKeys.getLong(1));
            }

            return transaction;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
