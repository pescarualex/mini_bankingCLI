package model;

public class IBAN {
    private int id;
    private String IBAN;
    private String account_id;


    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN){
        this.IBAN = IBAN;
    }

    public String getAccountID(){
        return account_id;
    }

    public void setAccountID(String accountID){
        this.account_id = accountID;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "IBAN='" + IBAN + '\'' +
                '}';
    }
}

