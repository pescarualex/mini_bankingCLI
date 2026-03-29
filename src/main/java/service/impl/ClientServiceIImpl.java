package service.impl;

import dao.ClientDAO;
import model.Client;
import utils.Utils;

import java.sql.SQLException;

public class ClientServiceIImpl {

    public Client createClient() throws SQLException {
        Client client = new Client();

        System.out.println("Enter first name: ");
        String firstName = Utils.readInput();
        client.setFirstName(firstName);

        System.out.println("Enter last name: ");
        String lastName = Utils.readInput();
        client.setLastName(lastName);

        System.out.println("Enter CNP: ");
        String CNP = Utils.readInput();
        client.setCNP(CNP);

        System.out.println("Enter series and Number of CI: ");
        String seriesAndNumberOfCI = Utils.readInput();
        client.setSeriesAndNumberOfCI(seriesAndNumberOfCI);

        ClientDAO.saveClient(client);

        Utils.logEntry("First Name: " + client.getFirstName() + ", Last Name: " + client.getLastName());

        return client;
    }
}
