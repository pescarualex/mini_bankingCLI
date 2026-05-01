package main;

import dao.*;
import db.DatabaseConnection;
import enums.Role;
import exceptions.*;
import model.Admin;
import model.Client;
import service.*;
import service.impl.*;
import utils.PasswordUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.Utils.readInputInteger;

public class Main {

    static AuditTrailDAO auditTrailDAO = new AuditTrailDAO();
    static AccountDAO accountDAO = new AccountDAO();
    static ClientDAO clientDAO = new ClientDAO();
    static CardDAO cardDAO = new CardDAO();
    static IbanDAO ibanDAO = new IbanDAO();
    static BankDAO bankDAO = new BankDAO();
    static AdminDAO adminDAO = new AdminDAO();

    static AuditTrailService auditTrailService = new AuditTrailServiceImpl(auditTrailDAO);
    static AccountService accountService = new AccountServiceImpl(accountDAO, clientDAO, cardDAO, ibanDAO);
    static BankService bankService = new BankServiceImpl(bankDAO);
    static CardService cardService = new CardServiceImpl(cardDAO);
    static IBANService ibanService = new IBANServiceImpl(ibanDAO);
    static ClientService clientService = new ClientServiceImpl(clientDAO, bankDAO, accountService, cardService, ibanService);
    static AdminService adminService = new AdminServiceImpl(adminDAO);


    public static void main(String[] args) throws ConnectionNotFoundException, ClientNotFoundException,
            AccountNotFoundException, AuditTrailNotFoundException, AdminNotFoundException {

        try(Connection connection = DatabaseConnection.getConnection()) {
            List<Admin> allAdmins = adminDAO.getAllAdmins(connection);
            if (allAdmins.isEmpty()){
                System.out.println("It looks like you have no administrator here.\n" +
                        "Let's create one.");
                adminService.createAdmin(connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login\n" +
                    "3. Admin\n" +
                    "0. Exit");

            int option = (int) readInputInteger();

            switch (option){
                case 1:
                    try {
                        try(Connection connection = DatabaseConnection.getConnection()){
                            clientService.register(connection);
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

    private static void admin(Connection connection) throws AuditTrailNotFoundException,
            ConnectionNotFoundException, AdminNotFoundException {
        System.out.println("To login, enter your username: ");
        String username = Utils.readInputString();
        System.out.println("Enter the password:");
        String password = Utils.readInputString();

        Admin adminByUsername = null;
        try {
            adminByUsername = adminDAO.getAdminByUsername(username, connection);
        } catch (SQLException e) {
            throw new AdminNotFoundException("Admin not found by username.", e);
        }

        if (adminByUsername == null) {
            System.out.println("No admin found.");
        } else if (adminByUsername.getRole() == Role.ADMIN && PasswordUtils.verifyPassword(password, adminByUsername.getPassword())) {
            while (true) {
                System.out.println("Welcome " + adminByUsername.getUsername() + "\n" +
                        "1. Pending users\n" +
                        "2. Full Audit Trail\n" +
                        "3. Audit Trail by client\n" +
                        "4. Add bank\n" +
                        "5. Create an Admin\n" +
                        "0. Exit");
                int option = (int) readInputInteger();

                switch (option) {
                    case 1:
                        try(Connection con = DatabaseConnection.getConnection()){
                            clientService.pendingClients(con);
                        } catch (SQLException | AuditTrailNotSavedException e){
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
                            adminService.createAdmin(con);
                        } catch (SQLException | AdminNotFoundException e){
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

    private static void login(Connection connection) throws ConnectionNotFoundException, ClientNotFoundException,
            AccountNotFoundException {
        System.out.println("Enter your username:");
        String username = Utils.readInputString();
        System.out.println("Enter password:");
        String password = Utils.readInputString();

        Client clientByUsername = null;

        try {
            clientByUsername = clientDAO.getClientByUsername(username, connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found.", e);
        }

        if(clientByUsername == null){
            System.out.println("No user found.");
            return;
        }

        if(clientByUsername.getUsername().equals(username) &&
                PasswordUtils.verifyPassword(password, clientByUsername.getPassword())){
            while(true) {
                System.out.println("Welcome, " + clientByUsername.getUsername());
                System.out.println("1. Deposit money\n" +
                        "2. Withdraw money\n" +
                        "3. Transfer money\n" +
                        "4. View account details\n" +
                        "5. Exit");
                int option = (int) readInputInteger();
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
