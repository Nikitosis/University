package mappers;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import entities.dao.CreditCard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountMapper {
    public static BankAccountMapper INSTANCE = new BankAccountMapper();

    private BankAccountMapper() {}

    public BankAccount resultSetToEntity(ResultSet resultSet) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getLong("id"));
        bankAccount.setStatus(BankAccountStatus.valueOf(resultSet.getString("status")));
        bankAccount.setBalance(resultSet.getBigDecimal("balance"));

        return bankAccount;
    }
}
