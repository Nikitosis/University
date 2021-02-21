package entities.request;

import java.math.BigDecimal;

public class CreditCardTransferRequest {
    private Long cardFromId;
    private Long cardToId;
    private BigDecimal amount;

    public Long getCardFromId() {
        return cardFromId;
    }

    public void setCardFromId(Long cardFromId) {
        this.cardFromId = cardFromId;
    }

    public Long getCardToId() {
        return cardToId;
    }

    public void setCardToId(Long cardToId) {
        this.cardToId = cardToId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
