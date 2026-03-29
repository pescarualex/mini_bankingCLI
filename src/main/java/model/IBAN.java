package model;

public class IBAN {
    private int id;
    private String IBAN;
    private int account_id;

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

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "id=" + id +
                ", IBAN='" + IBAN + '\'' +
                ", account_id='" + account_id + '\'' +
                '}';
    }
}

