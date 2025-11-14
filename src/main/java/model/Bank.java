package model;

import java.util.List;

public class Bank {
    private final String bankName;
    private final String bankSwift;
    private final String paymentNetwork;
    private final String bankCode;
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

    public String getPaymentNetwork() {
        return paymentNetwork;
    }

    public String getBankCode() {
        return bankCode;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
