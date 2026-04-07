package main;

import dao.AuditTrailDAO;
import dao.BankDAO;
import dao.ClientDAO;
import enums.Role;
import enums.Status;
import exceptions.CounterExceededException;
import model.AuditTrail;
import model.Bank;
import model.Client;
import service.impl.BankServiceImpl;
import service.impl.ClientServiceIImpl;
import utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws SQLException {

        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login\n" +
                    "3. Admin\n" +
                    "0. Exit");

            int option = Utils.readInputInteger();

            switch (option){
                case 1: register(); break;
                case 2: login(); break;
                case 3: admin(); break;
                case 0: System.exit(0);
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }
        }
    }

    private static void admin() throws SQLException {
        System.out.println("To login, enter your username: ");
        String username = Utils.readInputString();
        Client clientByUsername = ClientDAO.getClientByUsername(username);

        if (clientByUsername.getUsername() != null && !clientByUsername.getUsername().equals(username)){
            System.out.println("No user with this username was found.");
        } else if (clientByUsername.getUsername().equals(username) && clientByUsername.getRole().name() == "ADMIN") {
            System.out.println("Welcome " + clientByUsername.getUsername() + "\n" +
                    "1. Pending users\n" +
                    "2. Full Audit Trail\n" +
                    "3. Audit Trail by client\n" +
                    "4. Add bank\n"+
                    "5. Create an Admin\n" +
                    "0. Exit");
            int option = Utils.readInputInteger();

            switch (option){
                case 1: pendingClients(); break;
                case 2: fullAuditTrail(); break;
                case 3: auditTrailByClient(); break;
                case 4: addBank(); break;
                case 5: createAnotherAdmin(); break;
                case 0: System.exit(0);
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }
        } else {
            System.out.println("You don't have access to this feature.");
        }
    }

    private static void addBank() throws SQLException {
        BankServiceImpl.createBank();
    }

    private static void auditTrailByClient() throws SQLException {
        System.out.println("Enter the client ID: ");
        int clientID = Utils.readInputInteger();

        List<AuditTrail> auditTrailByClientID = AuditTrailDAO.getAuditTrailByClientID(clientID);
        for (AuditTrail entry : auditTrailByClientID){
            System.out.println(entry);
        }
    }

    private static void fullAuditTrail() throws SQLException {
        List<AuditTrail> allAuditTrail = AuditTrailDAO.getAllAuditTrail();
        for(AuditTrail entry : allAuditTrail){
            System.out.println(entry);
        }
    }

    private static void pendingClients() {
        boolean continueApproving = true;
        while(continueApproving) {
            System.out.println("Here are all pending clients that needs approval:");
            List<Client> clientsWhereStatusPending = ClientDAO.getClientsWhereStatusPending();
            if(clientsWhereStatusPending.isEmpty()){
                System.out.println("No clients pending.");
                continueApproving = false;
            } else {
                for (Client client : clientsWhereStatusPending) {
                    System.out.println(client);
                }
                System.out.println("To approve a client, enter the client ID:");
                int clientID = Utils.readInputInteger();
                ClientDAO.updateClientStatus(clientID, Status.APPROVED);
                System.out.println("Client approved successfully!\n" +
                        "Do you want to continue?");
                String s = Utils.readInputString();
                if(s.equalsIgnoreCase("no")){
                    continueApproving = false;
                }
            }
        }
    }

    private static void createAnotherAdmin() throws SQLException {
        Client client = ClientServiceIImpl.createClient(0);

        ClientDAO.updateClientRole(client.getId(), Role.ADMIN);
        ClientDAO.updateClientStatus(client.getId(), Status.APPROVED);

        System.out.println("Admin created successfully!");
    }


    private static void register() throws SQLException {
        System.out.println("Choose a bank: \n" +
                "1. BT\n" +
                "2. BCR");
        int option = Utils.readInputInteger();

        Bank bt = BankDAO.getBankByBankName("BT");
        Bank bcr = BankDAO.getBankByBankName("BCR");

        switch (option){
            case 1:
                ClientServiceIImpl.createClient(bt.getID());
                break;
            case 2:
                ClientServiceIImpl.createClient(bcr.getID());
                break;
            default:
                System.out.println("Incorrect selection. Please try again!");
        }
    }

    private static void login() throws SQLException {
        System.out.println("Enter your username:");
        String username = Utils.readInputString();
        System.out.println("Enter password:");
        String password = Utils.readInputString();

        Client clientByUsername = ClientDAO.getClientByUsername(username);

        if(clientByUsername.getUsername().equals(username) &&
                clientByUsername.getPassword().equals(password)){
            System.out.println("Welcome, " + clientByUsername.getUsername());
            System.out.println("1. Deposit money\n" +
                    "2. Withdraw money\n" +
                    "3. Transfer money\n" +
                    "4. View account details\n" +
                    "5. Exit");
            int option = Utils.readInputInteger();
            switch (option){
                case 1: depositMoney(); break;
                case 2: withdrawMoney(); break;
                case 3: transferMobey(); break;
                case 4: viewAccountDetails(); break;
                case 5: System.exit(0); break;
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }
        } else {
            System.out.println("No user found.");
        }
    }

    private static void viewAccountDetails() {
    }

    private static void transferMobey() {

    }

    private static void withdrawMoney() {

    }

    private static void depositMoney() {

    }


}
