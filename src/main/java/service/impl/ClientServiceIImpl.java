package service.impl;

import dao.BankDAO;
import dao.ClientDAO;
import enums.Role;
import enums.Status;
import exceptions.*;
import model.Account;
import model.Bank;
import model.Client;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.Utils.readInputInteger;

public class ClientServiceIImpl {

    public static Client createClient(int bankID, Connection connection) throws SQLException {
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

        int clientIDFromDB = ClientDAO.saveClient(client, connection);

        try {
            Utils.logEntry("First Name: " + client.getFirstName() +
                    ", Last Name: " + client.getLastName(), clientIDFromDB, connection);
        } catch (AuditTrailNotSavedException e) {
            System.out.println("Audit trail entry not saved for client creation.");
        }

        return client;
    }

    public void register(Connection connection) throws CounterExceededException, BankNotFoundException {
        List<Bank> banks = null;
        try {
            banks = BankDAO.getAllBanks(connection);
        } catch (SQLException e) {
            throw new BankNotFoundException("bank not found.", e);
        }

        System.out.println("Choose a bank: ");
        for(int i = 0; i < banks.size(); i++){
            System.out.println((i+1) + ". " + banks.get(i).getBankName());
        }
        String option = Utils.readInputString();

        Client client;
        Account account;

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            Bank bank = BankDAO.getBankByBankName(option, connection);
            client = ClientServiceIImpl.createClient(bank.getID(), connection);
            account = AccountServiceImpl.createAccount(client, connection);
            CardServiceImpl.createCard(bank, account, connection);
            IBANServiceImpl.createIban("RO", bank, account, connection);

            connection.commit();
        } catch (SQLException | IBANNotSavedException | AuditTrailNotSavedException e){
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void pendingClients(Connection connection) {
        boolean continueApproving = true;
        while(continueApproving) {
            System.out.println("Here are all pending clients that needs approval:");
            List<Client> clientsWhereStatusPending = null;
            try {
                clientsWhereStatusPending = ClientDAO.getClientsWhereStatusPending(connection);
            } catch (SQLException e){
                System.out.println("Error getting the clients with status Pending.");
            }

            if (clientsWhereStatusPending.isEmpty()) {
                System.out.println("No clients pending.");
                continueApproving = false;
            } else {
                for (Client client : clientsWhereStatusPending) {
                    System.out.println(client);
                }
                System.out.println("To approve a client, enter the client ID:");
                int clientID = readInputInteger();
                try {
                    ClientDAO.updateClientStatus(clientID, Status.APPROVED, connection);
                } catch (SQLException e) {
                    System.out.println("Updating the status of the client failed.");
                }
                System.out.println("Client approved successfully!\n" +
                        "Do you want to continue?");
                String s = Utils.readInputString();
                if (s.equalsIgnoreCase("no")) {
                    continueApproving = false;
                }
            }
        }
    }

    public void createAnotherAdmin(Connection connection) throws ClientNotFoundException, ClientNotUpdatedException {
        Client client = null;
        try {
            client = ClientServiceIImpl.createClient(0, connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found.", e);
        }

        try {
            ClientDAO.updateClientRole(client.getId(), Role.ADMIN, connection);
            ClientDAO.updateClientStatus(client.getId(), Status.APPROVED, connection);
            System.out.println("Admin created successfully!");
        } catch (SQLException e){
            throw new ClientNotUpdatedException("Client role and status not updated", e);
        }
    }
}
