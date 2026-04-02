package main;

import dao.*;
import enums.Role;
import enums.Status;
import exceptions.CounterExceededException;
import model.*;
import service.impl.*;
import utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Client> pendingClients = new ArrayList<>();

    public static void main(String[] args) throws CounterExceededException, SQLException {

        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login\n" +
                    "3. Admin");

            int option = Utils.readInputInteger();

            switch (option){
                case 1: register(); break;
                case 2: login(); break;
                case 3: admin(); break;
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }
        }


    }

    private static void admin() throws SQLException {
        System.out.println("To login, enter your username: ");
        String username = Utils.readInputString();
        Client clientByUsername = ClientDAO.getClientByUsername(username);

        if (!clientByUsername.getUsername().equals(username)){
            System.out.println("Noo user with this username was found.");
        } else if (clientByUsername.getUsername().equals(username) && clientByUsername.getRole().name() == "ADMIN") {
            System.out.println("Welcome " + clientByUsername + "\n" +
                    "1. Pending users\n" +
                    "2. Full Audit Trail\n" +
                    "3. Audit Trail by client");
            int option = Utils.readInputInteger();

            switch (option){
                case 1: pendingClients(); break;
                case 2: fullAuditTrail(); break;
                case 3: auditTrailByClient(); break;
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }

        } else {
            System.out.println("You don't have access to this feature.");
        }
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
                Client client = ClientServiceIImpl.createClient(bt.getID());
                client.setRole(Role.CLIENT);
                client.setStatus(Status.PENDING);
                pendingClients.add(client);
                break;
            case 2:
                Client client1 = ClientServiceIImpl.createClient(bcr.getID());
                client1.setRole(Role.CLIENT);
                client1.setStatus(Status.PENDING);
                pendingClients.add(client1);
                break;
            default:
                System.out.println("Incorrect selection. Please try again!");
        }
    }

    private static void login() {
        
    }


    private static void auditTrailByClient() {
    }

    private static void fullAuditTrail() {

    }

    private static void pendingClients() {

    }
}
