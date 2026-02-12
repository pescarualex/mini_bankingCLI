package model;


import utils.Utils;

public class Account {
    private int ID;
    private int client_ID;
    private long amountOfMoney;


    public int getID() {
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getClient_ID(){
        return client_ID;
    }

    public void setClient_ID(int client_ID){
        this.client_ID = client_ID;
    }


    public long getAmountOfMoney(){
        return amountOfMoney;
    }

    public void setAmountOfMoney(long money){
        this.amountOfMoney = money;
    }


}
