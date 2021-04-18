package repository;

import entities.dao.CreditCard;
import mappers.CreditCardMapper;
import mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreditCardRepository {
    public static CreditCardRepository INSTANCE = new CreditCardRepository();

    private CreditCardRepository() {}

    public List<CreditCard> findByUserId(Long id) {
        String command = "SELECT cc.id as ccId, cc.name as ccName, ba.id as baId," +
                " ba.balance as baBalance, ba.status as baStatus FROM credit_card cc LEFT JOIN bank_account ba ON cc.bank_account_id = ba.id WHERE ba.user_id = ?";

        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<CreditCard> cards = new ArrayList<>();
            while(resultSet.next()) {
                cards.add(CreditCardMapper.INSTANCE.resultSetToEntity(resultSet));
            }

            return cards;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public Optional<CreditCard> findById(Long id) {
        String command = "SELECT cc.id as ccId, cc.name as ccName, ba.id as baId," +
                " ba.balance as baBalance, ba.status as baStatus FROM credit_card cc LEFT JOIN bank_account ba ON cc.bank_account_id = ba.id WHERE cc.id=?";
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(command);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
               return Optional.empty();
            }

            return Optional.of(CreditCardMapper.INSTANCE.resultSetToEntity(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }

    public CreditCard create(CreditCard creditCard, Connection connection) {
        try{
            String command = "INSERT INTO credit_card (name, bank_account_id) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(command, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, creditCard.getName());
            preparedStatement.setLong(2, creditCard.getBankAccount().getId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.next()) {
                creditCard.setId(generatedKeys.getLong(1));
            }

            return creditCard;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public CreditCard create(CreditCard creditCard) {
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        try {
            return create(creditCard, connection);
        } finally {
            ConnectionPool.INSTANCE.releaseConnection(connection);
        }
    }
}
