package model;

import utils.Utils;

import java.time.LocalDate;

public class Card {
    private int id;
    private String cardNumber;
    private String pin_code;
    private LocalDate expirationDate;
    private String CVV;
    private int account_ID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public int getAccount_ID() {
        return account_ID;
    }

    public void setAccount_ID(int account_ID) {
        this.account_ID = account_ID;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", pin_code='" + pin_code + '\'' +
                ", expirationDate=" + expirationDate +
                ", CVV='" + CVV + '\'' +
                ", account_ID=" + account_ID +
                '}';
    }
}




