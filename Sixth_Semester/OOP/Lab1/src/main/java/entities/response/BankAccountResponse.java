package entities.response;

import entities.dao.BankAccountStatus;
import entities.dao.User;

import java.math.BigDecimal;

public class BankAccountResponse {
    private Long id;
    private BigDecimal balance;
    private BankAccountStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BankAccountStatus getStatus() {
        return status;
    }

    public void setStatus(BankAccountStatus status) {
        this.status = status;
    }
}
