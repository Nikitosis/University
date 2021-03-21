package entities.response;

public class CreditCardResponse {
    private Long id;
    private String name;
    private BankAccountResponse bankAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BankAccountResponse getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountResponse bankAccount) {
        this.bankAccount = bankAccount;
    }
}
