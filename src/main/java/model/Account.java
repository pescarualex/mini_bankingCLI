package model;


import utils.Utils;

public class Account {
    private final String ID;
    private IBAN iban;
    private Card card;


    public Account(String bankCode){
        this.ID = bankCode + Utils.generateNumbers(8);
    }

    public String getID() {
        return ID;
    }

    public IBAN getIban() {
        return iban;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setIban(IBAN iban) {
        this.iban = iban;
    }
}
