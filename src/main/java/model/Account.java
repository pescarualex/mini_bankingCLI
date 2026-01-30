package model;


import utils.Utils;

public class Account {
    private final String ID;
    private long amountOfMoney;

    /// add deposit, money, transfer, etc
    private IBAN iban;
    private Card card;


    public Account(String bankID){
        this.ID = bankID + Utils.generateNumbers(8);
    }

    public String getID() {
        return ID;
    }

    public IBAN getIban() {
        return iban;
    }

    public long getAmountOfMoney(){
        return amountOfMoney;
    }

    public void setAmountOfMoney(long money){
        this.amountOfMoney = money;
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
