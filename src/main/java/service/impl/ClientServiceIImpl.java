package service.impl;

import model.Account;
import model.Client;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientServiceIImpl {

    AccountServiceImpl accountService = new AccountServiceImpl();

    static List<Client> BTclients = new ArrayList<>();
    static List<Client> BCRclients = new ArrayList<>();
    static List<Client> INGclients = new ArrayList<>();
    static List<Client> BRDclients = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);


    public Client createClient(String bankID){
        Client client = new Client();

        System.out.println("Enter first name: ");
        String firstName = scanner.nextLine();
        client.setFirstName(firstName);

        System.out.println("Enter last name: ");
        String lastName = scanner.nextLine();
        client.setLastName(lastName);

        System.out.println("Enter CNP: ");
        String CNP = scanner.nextLine();
        client.setCNP(CNP);

        System.out.println("Enter series and Number of CI: ");
        String seriesAndNumberOfCI = scanner.nextLine();
        client.setSeriesAndNumberOfCI(seriesAndNumberOfCI);

        Account account = accountService.createAccount(bankID);

        client.setAccount(account);

        Utils.logEntry("\t-> First Name: " + client.getFirstName() + ", Last Name: " + client.getLastName() + "\n"
                + "\t-> Account ID: " + client.getAccount().getID() + ", created for: " + client.getUsername() + ", "
                + "with IBAN: " + client.getAccount().getIban().getIBAN() + ", and Card no.: " +
                client.getAccount().getCard().getCardNumber());

        return client;
    }
}
