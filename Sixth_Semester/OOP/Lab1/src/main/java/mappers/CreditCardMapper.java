package mappers;

import entities.dao.CreditCard;
import entities.dao.User;
import entities.response.CreditCardResponse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CreditCardMapper {
    public static CreditCardMapper INSTANCE = new CreditCardMapper();

    private CreditCardMapper() {}

    public CreditCard resultSetToEntity(ResultSet resultSet) throws SQLException {
        CreditCard card = new CreditCard();
        card.setId(resultSet.getLong("id"));
        card.setName(resultSet.getString("name"));

        return card;
    }

    public CreditCardResponse toResponse(CreditCard creditCard) {
        CreditCardResponse response = new CreditCardResponse();
        response.setId(creditCard.getId());
        response.setName(creditCard.getName());

        return response;
    }
}
