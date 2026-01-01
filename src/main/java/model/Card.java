package model;

import utils.Utils;

import java.time.LocalDate;

public class Card {
    private String cardNumber;
    private String pin_code;
    private LocalDate expirationDate;
    private String CVV;

    public String getCardNumber(){
        return cardNumber;
    }

    public String getPinCode(){
        return pin_code;
    }

    public LocalDate getExpirationDate(){
        return expirationDate;
    }

    public String getCVV(){
        return CVV;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardNumber='" + cardNumber + '\'' +
                ", pin_code=" + pin_code +
                ", expirationDate=" + expirationDate +
                ", CVV=" + CVV +
                '}';
    }
}




