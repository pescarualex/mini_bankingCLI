package main;

import dao.*;
import enums.Role;
import enums.Status;
import exceptions.CounterExceededException;
import model.*;
import service.impl.*;
import utils.Utils;

import java.sql.SQLException;
import java.util.List;

import static utils.Utils.readInputInteger;

public class Main {


    public static void main(String[] args) throws SQLException, CounterExceededException {

        while(true){
            System.out.println("Welcome!\n" +
                    "1. Register\n" +
                    "2. Login\n" +
                    "3. Admin\n" +
                    "0. Exit");

            int option = readInputInteger();

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

    private static void register() throws SQLException, CounterExceededException {
        System.out.println("Choose a bank: \n" +
                "1. BT\n" +
                "2. BCR");
        int option = readInputInteger();

        Bank bt = BankDAO.getBankByBankName("BT");
        Bank bcr = BankDAO.getBankByBankName("BCR");

        Client client;
        Account account;

        switch (option){
            case 1:
                client = ClientServiceIImpl.createClient(bt.getID());
                account = AccountServiceImpl.createAccount(client);
                CardServiceImpl.createCard(bt, account);
                IBANServiceImpl.createIban("RO", bt, account);
                break;
            case 2:
                client = ClientServiceIImpl.createClient(bcr.getID());
                account = AccountServiceImpl.createAccount(client);
                CardServiceImpl.createCard(bt, account);
                IBANServiceImpl.createIban("RO", bt, account);
                break;
            default:
                System.out.println("Incorrect selection. Please try again!");
        }
    }

    private static void admin() throws SQLException {
        System.out.println("To login, enter your username: ");
        String username = Utils.readInputString();
        Client clientByUsername = ClientDAO.getClientByUsername(username);

        if (clientByUsername.getUsername() != null && !clientByUsername.getUsername().equals(username)){
            System.out.println("No user with this username was found.");
        } else if (clientByUsername.getUsername().equals(username) && clientByUsername.getRole().name() == "ADMIN") {
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
                        pendingClients();
                        break;
                    case 2:
                        fullAuditTrail();
                        break;
                    case 3:
                        auditTrailByClient();
                        break;
                    case 4:
                        addBank();
                        break;
                    case 5:
                        createAnotherAdmin();
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

    private static void addBank() throws SQLException {
        BankServiceImpl.createBank();
    }

    private static void auditTrailByClient() throws SQLException {
        boolean next = true;

        while(next) {
            System.out.println("Enter the client ID: ");
            int clientID = readInputInteger();

            List<AuditTrail> auditTrailByClientID = AuditTrailDAO.getAuditTrailByClientID(clientID);
            for (AuditTrail entry : auditTrailByClientID) {
                System.out.println(entry);
            }

            System.out.println("Do you want to continue?");
            String s = Utils.readInputString();
            if(s.equalsIgnoreCase("no")){
                next = false;
            }
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
                int clientID = readInputInteger();
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

    private static void login() throws SQLException {
        System.out.println("Enter your username:");
        String username = Utils.readInputString();
        System.out.println("Enter password:");
        String password = Utils.readInputString();

        Client clientByUsername = ClientDAO.getClientByUsername(username);

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
                        depositMoney(clientByUsername);
                        break;
                    case 2:
                        withdrawMoney(clientByUsername);
                        break;
                    case 3:
                        transferMoney(clientByUsername);
                        break;
                    case 4:
                        viewAccountDetails(clientByUsername);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Incorrect selection. Please try again!");
                }
            }
        } else {
            System.out.println("No user found.");
        }
    }

    private static void viewAccountDetails(Client client) throws SQLException {
        System.out.print("To proceed, you need to confirm your password\n" +
                "->");
        String password = Utils.readInputString();
        Client clientByID = ClientDAO.getClientByID(client.getId());

        if (password != null && clientByID.getPassword().equals(password)){
            System.out.println("Client Information:");
            System.out.println("First name: " + clientByID.getFirstName() + ", Last name: " + clientByID.getLastName() + "\n" +
                    "CNP: " + clientByID.getCNP() + ", Series and CI Number: " + clientByID.getSeriesAndNumberOfCI() + "\n" +
                    "Username: " + clientByID.getUsername() + ", Role: " + clientByID.getRole().name() + ", Status: " + clientByID.getStatus().name() + "\n" +
                    "Bank ID: " + clientByID.getBankID());

            Account accountByClientID = AccountDAO.getAccountByClientID(clientByID.getId());
            System.out.println("Account information:");
            System.out.println("Money: " + accountByClientID.getAmountOfMoney());

            System.out.println("Card information: ");
            Card cardByAccountID = CardDAO.getCardByAccountID(accountByClientID.getID());
            System.out.println("Card Number: " + cardByAccountID.getCardNumber() + ", Card Expiration Period: " + cardByAccountID.getExpirationDate() + "\n" +
                    "Card Pin Code: " + cardByAccountID.getPin_code() + ", Card CVV: " + cardByAccountID.getCVV());

            System.out.println("IBAN information: ");
            IBAN ibanByAccountID = IbanDAO.getIbanByAccountID(accountByClientID.getID());
            System.out.println("IBAN: " + ibanByAccountID.getIBAN());
        }

    }

    private static void transferMoney(Client client) throws SQLException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        System.out.println("Enter the IBAN:");
        String iban = Utils.readInputString();

        System.out.println("Enter the account holder username: ");
        String holderUsername = Utils.readInputString();

        System.out.println("Amount of money that you want to transfer: ");
        int ammountOfMoney = Utils.readInputInteger();

        Client clientByID = ClientDAO.getClientByUsername(holderUsername);
        if(clientByID.getUsername() != null && clientByID.getUsername().equals(holderUsername)){
            Account accountByClientID = AccountDAO.getAccountByClientID(clientByID.getId());
            IBAN ibanByAccountID = IbanDAO.getIbanByAccountID(accountByClientID.getID());
            if(ibanByAccountID.getIBAN() != null && ibanByAccountID.getIBAN().equals(iban)){
                long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + ammountOfMoney;
                AccountDAO.updateAmmountOfMoney(clientByID.getId(), totalAmmountOfMoney);

                Account accountByClientID1 = AccountDAO.getAccountByClientID(client.getId());
                long currentUserAmountMoney = accountByClientID1.getAmountOfMoney() - ammountOfMoney;
                AccountDAO.updateAmmountOfMoney(client.getId(), currentUserAmountMoney);
                System.out.println("Transfer completed successfully!");

                AuditTrail auditTrail = Utils.logEntry("Transferred money to " + clientByID.getId() + ", " + clientByID.getUsername(), client.getId());
                AuditTrailDAO.saveAuditTrail(auditTrail);
            } else {
                System.out.println("No IBAN was found.");
            }
        } else {
            System.out.println("No client was found.");
        }
    }

    private static void withdrawMoney(Client client) throws SQLException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = AccountDAO.getAccountByClientID(client.getId());
        System.out.println("How much money do you want to withdraw?");
        int money = readInputInteger();
        long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() - (long) money;

        AccountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney);

        System.out.println("Withdraw successfully!");

        AuditTrail auditTrail = Utils.logEntry("Withdraw ammount of money: " + money, client.getId());
        AuditTrailDAO.saveAuditTrail(auditTrail);
    }

    private static void depositMoney(Client client) throws SQLException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = AccountDAO.getAccountByClientID(client.getId());

        System.out.println("How much money do you want to deposit?");
        int deposit = readInputInteger();
        long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + (long) deposit;
        AccountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney);

        System.out.println("Money are in account ;)");

        AuditTrail auditTrail = Utils.logEntry("Deposit ammount of money: " + deposit, client.getId());
        AuditTrailDAO.saveAuditTrail(auditTrail);
    }


}
