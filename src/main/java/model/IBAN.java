package model;

import utils.Utils;


public class IBAN {
    private final String IBAN;

    public IBAN(String bankName) {
        this.IBAN = generateIBAN("RO", bankName);
    }

    private String generateIBAN(String country, String bankName){
        String iban = "";

        String checkDigits = Utils.generateNumbers(2);
        String BBAN1 = Utils.generateNumbers(4);
        String BBAN2 = Utils.generateNumbers(4);
        String BBAN3 = Utils.generateNumbers(4);
        String BBAN4 = Utils.generateNumbers(4);

        iban = country + checkDigits + " " + bankName +
                " " + BBAN1 + " " + BBAN2 + " " + BBAN3 + " " + BBAN4;
        return iban;
    }

    public String getIBAN() {
        return IBAN;
    }

    @Override
    public String toString() {
        return "IBAN{" +
                "IBAN='" + IBAN + '\'' +
                '}';
    }
}

