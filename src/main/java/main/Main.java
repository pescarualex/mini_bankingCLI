package main;

import dao.ClientDAO;
import db.DatabaseConnection;
import enums.Role;
import exceptions.*;
import model.Client;
import service.impl.AccountServiceImpl;
import service.impl.AuditTrailServiceImpl;
import service.impl.BankServiceImpl;
import service.impl.ClientServiceIImpl;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import static utils.Utils.readInputInteger;

public class Main {

    static ClientServiceIImpl clientServiceI = new ClientServiceIImpl();
    static AuditTrailServiceImpl auditTrailService = new AuditTrailServiceImpl();
    static AccountServiceImpl accountService = new AccountServiceImpl();
    static BankServiceImpl bankService = new BankServiceImpl();


    public static void main(String[] args) throws ConnectionNotFoundException, ClientNotFoundException,
            AuditTrailNotSavedException, AccountNotFoundException,
            BankNotCreatedException, ClientNotUpdatedException,
            AuditTrailNotFoundException {

        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login\n" +
                    "3. Admin\n" +
                    "0. Exit");

            int option = readInputInteger();

            switch (option){
                case 1:
                    try {
                        try(Connection connection = DatabaseConnection.getConnection()){
                            clientServiceI.register(connection);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed", e);
                        }
                    } catch (CounterExceededException e) {
                        System.out.println("Generating IBAN failed.");
                    } catch (BankNotFoundException e) {
                        System.out.println("Bank not found.");
                    }
                    break;
                case 2:
                    try(Connection connection = DatabaseConnection.getConnection()){
                        login(connection);
                    } catch (SQLException e){
                        throw new ConnectionNotFoundException("Connection failed", e);
                    }
                    break;
                case 3:
                    try(Connection connection = DatabaseConnection.getConnection()){
                        admin(connection);
                    } catch (SQLException e){
                        throw new ConnectionNotFoundException("Connection failed", e);
                    }
                    break;
                case 0: System.exit(0);
                default:
                    System.out.println("Incorrect selection. Please try again!");
            }
        }
    }

    private static void admin(Connection connection) throws ClientNotFoundException, AuditTrailNotFoundException, ClientNotUpdatedException, BankNotCreatedException, ConnectionNotFoundException {
        System.out.println("To login, enter your username: ");
        String username = Utils.readInputString();

        Client clientByUsername = null;
        try {
            clientByUsername = ClientDAO.getClientByUsername(username, connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found by username.", e);
        }

        if (clientByUsername.getUsername() == null) {
            System.out.println("No user found with this username");
            return;
        } else if (clientByUsername.getRole() == Role.ADMIN) {
            while (true) {
                System.out.println("Welcome " + clientByUsername.getUsername() + "\n" +
                        "1. Pending users\n" +
                        "2. Full Audit Trail\n" +
                        "3. Audit Trail by client\n" +
                        "4. Add bank\n" +
                        "5. Create an Admin\n" +
                        "0. Exit");
                int option = readInputInteger();

                switch (option) {
                    case 1:
                        try(Connection con = DatabaseConnection.getConnection()){
                            clientServiceI.pendingClients(con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 2:
                        try(Connection con = DatabaseConnection.getConnection()){
                            auditTrailService.fullAuditTrail(con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 3:
                        try(Connection con = DatabaseConnection.getConnection()){
                            auditTrailService.auditTrailByClient(con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 4:
                        try(Connection con = DatabaseConnection.getConnection()){
                            bankService.addBank(con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 5:
                        try(Connection con = DatabaseConnection.getConnection()){
                            clientServiceI.createAnotherAdmin(con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Incorrect selection. Please try again!");
                }
            }
        } else {
            System.out.println("You don't have access to this feature.");
        }
    }

    private static void login(Connection connection) throws ConnectionNotFoundException, ClientNotFoundException, AuditTrailNotSavedException, AccountNotFoundException {
        System.out.println("Enter your username:");
        String username = Utils.readInputString();
        System.out.println("Enter password:");
        String password = Utils.readInputString();

        Client clientByUsername = null;

        try {
            clientByUsername = ClientDAO.getClientByUsername(username, connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found.", e);
        }

        if(clientByUsername.getUsername() == null){
            System.out.println("No user found with this username");
            return;
        }

        if(clientByUsername.getUsername().equals(username) &&
                clientByUsername.getPassword().equals(password)){
            while(true) {
                System.out.println("Welcome, " + clientByUsername.getUsername());
                System.out.println("1. Deposit money\n" +
                        "2. Withdraw money\n" +
                        "3. Transfer money\n" +
                        "4. View account details\n" +
                        "5. Exit");
                int option = readInputInteger();
                switch (option) {
                    case 1:
                        try(Connection con = DatabaseConnection.getConnection()){
                            accountService.depositMoney(clientByUsername, con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 2:
                        try(Connection con = DatabaseConnection.getConnection()){
                            accountService.withdrawMoney(clientByUsername, con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 3:
                        try(Connection con = DatabaseConnection.getConnection()){
                            accountService.transferMoney(clientByUsername, con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 4:
                        try(Connection con = DatabaseConnection.getConnection()){
                            accountService.viewAccountDetails(clientByUsername, con);
                        } catch (SQLException e){
                            throw new ConnectionNotFoundException("Connection failed.", e);
                        }
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Incorrect selection. Please try again!");
                }
            }
        } else {
            System.out.println("Incorrect username or password.");
        }
    }
}
