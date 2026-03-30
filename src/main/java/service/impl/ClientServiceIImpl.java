package service.impl;

import dao.AuditTrailDAO;
import dao.ClientDAO;
import model.AuditTrail;
import model.Client;
import utils.Utils;

import java.sql.SQLException;

public class ClientServiceIImpl {

    public Client createClient() throws SQLException {
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

        ClientDAO.saveClient(client);

        AuditTrail auditTrail = Utils.logEntry("First Name: " + client.getFirstName() +
                ", Last Name: " + client.getLastName(), client.getId());
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return client;
    }
}
