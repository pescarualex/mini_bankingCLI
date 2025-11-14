package model;


import utils.Utils;

public class Account {
    private int ID;
    private IBAN iban;
    private Card card;


    public Account(){
        this.ID = Integer.parseInt(Utils.generateNumbers(8));
    }







    public int getID() {
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
