package service.impl;

import dao.AuditTrailDAO;
import dao.ClientDAO;
import model.AuditTrail;
import model.Client;
import utils.Utils;

import java.sql.SQLException;

public class ClientServiceIImpl {

    public static Client createClient(int bankID) throws SQLException {
        Client client = new Client();

        System.out.println("Enter first name: ");
        String firstName = Utils.readInputString();
        client.setFirstName(firstName);

        System.out.println("Enter last name: ");
        String lastName = Utils.readInputString();
        client.setLastName(lastName);

        System.out.println("Enter CNP: ");
        String CNP = Utils.readInputString();
        client.setCNP(CNP);

        System.out.println("Enter series and Number of CI: ");
        String seriesAndNumberOfCI = Utils.readInputString();
        client.setSeriesAndNumberOfCI(seriesAndNumberOfCI);

        System.out.println("Set a username:");
        String username = Utils.readInputString();
        client.setUsername(username);

        client.setBankID(bankID);

        int clientIDFromDB = ClientDAO.saveClient(client);

        AuditTrail auditTrail = Utils.logEntry("First Name: " + client.getFirstName() +
                ", Last Name: " + client.getLastName(), clientIDFromDB);
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return client;
    }
}
