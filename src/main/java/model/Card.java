package model;

import utils.Utils;

import java.time.LocalDate;

public class Card {
    private int id;
    private String cardNumber;
    private String pin_code;
    private LocalDate expirationDate;
    private String CVV;
    private String account_ID;

    public int getCardID(){
        return id;
    }

    public String getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(String cardNumber){
        this.cardNumber = cardNumber;
    }

    public String getPinCode(){
        return pin_code;
    }

    public void setPinCode(String pinCode) {
        this.pin_code = pinCode;
    }

    public LocalDate getExpirationDate(){
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate){
        this.expirationDate = expirationDate;
    }

    public String getCVV(){
        return CVV;
    }

    public void setCVV(String CVV){
        this.CVV = CVV;
    }

    public String getAccountID(){
        return account_ID;
    }

    public void setAccountID(){
        this.account_ID = account_ID;
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




