package model;

import utils.Utils;

import java.util.List;

public class Bank {
    private int ID;
    private final String bankName;
    private final String bankSwift;
    private final String paymentNetwork;
    private final String bankCode;
    private int account_ID;
    private int client_ID;


    public Bank(String bankName, String bankSwift,
                String bankCode, String paymentNetwork) {
        this.bankName = bankName;
        this.bankSwift = bankSwift;
        this.bankCode = bankCode;
        this.paymentNetwork = paymentNetwork;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id){
        this.ID = id;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankSwift(){
        return bankSwift;
    }

    public String getPaymentNetwork() {
        return paymentNetwork;
    }

    public String getBankCode() {
        return bankCode;
    }

    public int getClientID() {
        return client_ID;
    }

    public void setClientID(int client_ID) {
        this.client_ID = client_ID;
    }

    public int getAccountID(){
        return account_ID;
    }

    public void setAccountID(int account_ID){
        this.account_ID = account_ID;
    }
}
