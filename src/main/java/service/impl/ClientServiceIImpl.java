package service.impl;

import exceptions.CounterExceededException;
import model.Client;
import utils.Utils;

import java.util.Scanner;

public class ClientServiceIImpl {

    AccountServiceImpl accountService = new AccountServiceImpl();
    Scanner scanner = new Scanner(System.in);

    public Client createClient() throws CounterExceededException {
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

        Utils.logEntry("First Name: " + client.getFirstName() + ", Last Name: " + client.getLastName());

        return client;
    }
}
