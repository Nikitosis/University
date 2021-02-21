package entities.dao;

import java.math.BigDecimal;

public class BankAccount {
    private Long id;
    private User user;
    private BigDecimal balance;
    private BankAccountStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
