package service.impl;

import model.Account;
import model.Client;
import utils.Utils;

public class ClientServiceIImpl {


    public Client createClient(String firstName, String lastName,
                               String CNP, String seriesAndNumber,
                               Account account){
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setCNP(CNP);
        client.setSeriesAndNumberOfCI(seriesAndNumber);
        client.setAccount(account);

        Utils.logEntry("\t-> First Name: " + client.getFirstName() + ", Last Name: " + client.getLastName() + "\n"
                + "\t-> Account ID: " + client.getAccount().getID() + ", created for: " + client.getUsername() + ", "
                + "with IBAN: " + client.getAccount().getIban().getIBAN() + ", and Card no.: " +
                client.getAccount().getCard().getCardNumber());

        return client;
    }
}
