package mappers;

import entities.dao.BankAccount;
import entities.dao.BankAccountStatus;
import entities.dao.CreditCard;
import entities.dao.User;
import entities.response.BankAccountResponse;
import entities.response.CreditCardResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardMapper {
    public static CreditCardMapper INSTANCE = new CreditCardMapper();

    private CreditCardMapper() {}

    public CreditCard resultSetToEntity(ResultSet resultSet) throws SQLException {
        CreditCard card = new CreditCard();
        card.setId(resultSet.getLong("ccId"));
        card.setName(resultSet.getString("ccName"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(resultSet.getLong("baId"));
        bankAccount.setBalance(resultSet.getBigDecimal("baBalance"));
        bankAccount.setStatus(BankAccountStatus.valueOf(resultSet.getString("baStatus")));

        card.setBankAccount(bankAccount);

        return card;
    }

    public CreditCardResponse toResponse(CreditCard creditCard) {
        CreditCardResponse response = new CreditCardResponse();
        response.setId(creditCard.getId());
        response.setName(creditCard.getName());

        if(creditCard.getBankAccount() != null) {
            BankAccountResponse bankAccountResponse = new BankAccountResponse();
            bankAccountResponse.setId(creditCard.getBankAccount().getId());
            bankAccountResponse.setBalance(creditCard.getBankAccount().getBalance());
            bankAccountResponse.setStatus(creditCard.getBankAccount().getStatus());
            response.setBankAccount(bankAccountResponse);
        }

        return response;
    }
}
