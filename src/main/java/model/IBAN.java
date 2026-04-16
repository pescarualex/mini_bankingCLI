package model;

public class IBAN {
    private int id;
    private String IBAN;
    private int accountId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "id=" + id +
                ", IBAN='" + IBAN + '\'' +
                ", account_id='" + accountId + '\'' +
                '}';
    }
}

