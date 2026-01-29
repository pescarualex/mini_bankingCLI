package model;

public class IBAN {
    private String IBAN;


    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN){
        this.IBAN = IBAN;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "IBAN='" + IBAN + '\'' +
                '}';
    }
}

