package model;


import utils.Utils;

public class Account {
    private final String ID;
    private int client_ID;
    private long amountOfMoney;
    private IBAN iban;
    private Card card;


    public Account(String bankID){
        this.ID = bankID + Utils.generateNumbers(8);
    }

    public String getID() {
        return ID;
    }

    public int getClient_ID(){
        return client_ID;
    }

    public void setClient_ID(int client_ID){
        this.client_ID = client_ID;
    }


    public IBAN getIban() {
        return iban;
    }

    public void setIban(IBAN iban) {
        this.iban = iban;
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


}
