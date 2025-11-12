package model;

import java.util.List;

public class Bank {
    private String bankName;
    private String bankSwift;
    private String paymentNetwork;
    private String bankCode;
    private List<Client> clients;


    public Bank(String bankName, String bankSwift,
                String bankCode, String paymentNetwork) {
        this.bankName = bankName;
        this.bankSwift = bankSwift;
        this.bankCode = bankCode;
        this.paymentNetwork = paymentNetwork;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankSwift() {
        return bankSwift;
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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
