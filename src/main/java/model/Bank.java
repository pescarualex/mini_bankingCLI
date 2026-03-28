package model;

import utils.Utils;

import java.util.List;

public class Bank {
    private int id;
    private String bankName;
    private String bankSwift;
    private String paymentNetwork;
    private String bankCode;
    private int account_ID;
    private int client_ID;

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankSwift() {
        return bankSwift;
    }

    public void setBankSwift(String bankSwift) {
        this.bankSwift = bankSwift;
    }

    public String getPaymentNetwork() {
        return paymentNetwork;
    }

    public void setPaymentNetwork(String paymentNetwork) {
        this.paymentNetwork = paymentNetwork;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public int getAccountID() {
        return account_ID;
    }

    public void setAccountID(int account_ID) {
        this.account_ID = account_ID;
    }

    public int getClientID() {
        return client_ID;
    }

    public void setClientID(int client_ID) {
        this.client_ID = client_ID;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "ID=" + id +
                ", bankName='" + bankName + '\'' +
                ", bankSwift='" + bankSwift + '\'' +
                ", paymentNetwork='" + paymentNetwork + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", account_ID=" + account_ID +
                ", client_ID=" + client_ID +
                '}';
    }
}
