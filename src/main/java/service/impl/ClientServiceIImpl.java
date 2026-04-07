package service.impl;

import dao.AuditTrailDAO;
import dao.ClientDAO;
import enums.Role;
import enums.Status;
import model.AuditTrail;
import model.Client;
import utils.Utils;

import java.sql.SQLException;

public class ClientServiceIImpl {

    public static Client createClient(int bankID) throws SQLException {
        Client client = new Client();

        System.out.println("Enter first name: ");
        client.setFirstName(Utils.readInputString());

        System.out.println("Enter last name: ");
        client.setLastName(Utils.readInputString());

        System.out.println("Enter CNP: ");
        client.setCNP(Utils.readInputString());

        System.out.println("Enter series and Number of CI: ");
        client.setSeriesAndNumberOfCI(Utils.readInputString());

        System.out.println("Set a username:");
        client.setUsername(Utils.readInputString());

        System.out.println("Set a password:");
        client.setPassword(Utils.readInputString());

        client.setBankID(bankID);
        client.setRole(Role.CLIENT);
        client.setStatus(Status.PENDING);

        int clientIDFromDB = ClientDAO.saveClient(client);

        AuditTrail auditTrail = Utils.logEntry("First Name: " + client.getFirstName() +
                ", Last Name: " + client.getLastName(), clientIDFromDB);
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return client;
    }
}
