package model;


import utils.Utils;

public class Account {
    private final String ID;
    private String client_ID;
    private long amountOfMoney;


    public Account(String bankID){
        this.ID = bankID + Utils.generateNumbers(8);
    }

    public String getID() {
        return ID;
    }

    public String getClient_ID(){
        return client_ID;
    }

    public void setClient_ID(String client_ID){
        this.client_ID = client_ID;
    }


    public long getAmountOfMoney(){
        return amountOfMoney;
    }

    public void setAmountOfMoney(long money){
        this.amountOfMoney = money;
    }


}
