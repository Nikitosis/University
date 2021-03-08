package entities.request;

import java.math.BigDecimal;

public class CreditCardTopUpRequest {
    private Long cardId;
    private BigDecimal amount;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
