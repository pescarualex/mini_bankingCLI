package model;

import java.util.List;

public class Bank {
    private String bankName;
    private String bankSwift;
    private List<Client> clients;


    public Bank(String bankName, String bankSwift){
        this.bankName = bankName;
        this.bankSwift = bankSwift;
    }

    public void createAccount(Client client){

    }


    public String getBankName() {
        return bankName;
    }

    public String getBankSwift() {
        return bankSwift;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setAccounts(List<Client> clients) {
        this.clients = clients;
    }
}
