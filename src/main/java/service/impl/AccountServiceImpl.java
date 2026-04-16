package service.impl;

import dao.AccountDAO;
import dao.CardDAO;
import dao.ClientDAO;
import dao.IbanDAO;
import enums.Status;
import exceptions.AccountNotFoundException;
import exceptions.AuditTrailNotSavedException;
import exceptions.ClientNotFoundException;
import model.Account;
import model.Card;
import model.Client;
import model.IBAN;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import static utils.Utils.readInputInteger;

public class AccountServiceImpl {

    public static Account createAccount(Client client, Connection connection) throws SQLException {
        Account account = new Account();
        account.setAmountOfMoney(0L);
        account.setClient_ID(client.getId());

        if(account != null) {
            AccountDAO.saveAccount(account, connection);
            try {
                Utils.logEntry("Account created for: " + client.getId(), client.getId(), connection);
            } catch (AuditTrailNotSavedException e) {
                System.out.println("Audit trail entry not saved.");
            }
        } else {
            System.out.println("Incorrect account");
        }

        return account;
    }


    public void viewAccountDetails(Client client, Connection connection) {
        System.out.print("To proceed, you need to confirm your password\n" +
                "->");
        String password = Utils.readInputString();

        try {
            Client clientByID = ClientDAO.getClientByID(client.getId(), connection);

            if (password != null && clientByID.getPassword().equals(password)) {
                System.out.println("Client Information:");
                System.out.println("First name: " + clientByID.getFirstName() + ", Last name: " + clientByID.getLastName() + "\n" +
                        "CNP: " + clientByID.getCNP() + ", Series and CI Number: " + clientByID.getSeriesAndNumberOfCI() + "\n" +
                        "Username: " + clientByID.getUsername() + ", Role: " + clientByID.getRole().name() + ", Status: " + clientByID.getStatus().name() + "\n" +
                        "Bank ID: " + clientByID.getBankID());

                Account accountByClientID = AccountDAO.getAccountByClientID(clientByID.getId(), connection);
                System.out.println("Account information:");
                System.out.println("Money: " + accountByClientID.getAmountOfMoney());

                System.out.println("Card information: ");
                Card cardByAccountID = CardDAO.getCardByAccountID(accountByClientID.getID(), connection);
                System.out.println("Card Number: " + cardByAccountID.getCardNumber() + ", Card Expiration Period: " + cardByAccountID.getExpirationDate() + "\n" +
                        "Card Pin Code: " + cardByAccountID.getPin_code() + ", Card CVV: " + cardByAccountID.getCVV());

                System.out.println("IBAN information: ");
                IBAN ibanByAccountID = IbanDAO.getIbanByAccountID(accountByClientID.getID(), connection);
                System.out.println("IBAN: " + ibanByAccountID.getIBAN());
            }
        } catch (SQLException e){
            System.out.println("Account details not available this time.");
        }

    }

    public void transferMoney(Client client, Connection connection) throws AccountNotFoundException {
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
        Account accountOfCurrentUser;
        try {
            accountOfCurrentUser = AccountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Account not found", e);
        }

        if(accountOfCurrentUser.getAmountOfMoney() < ammountOfMoney){
            System.out.println("You don't have that amount in your account");
            return;
        } else {
            try {
                connection.setAutoCommit(false);
                Client clientByID = ClientDAO.getClientByUsername(holderUsername, connection);

                if(clientByID == null){
                    System.out.println("No holder found with this username.");
                    return;
                } else if (clientByID.getUsername() != null && clientByID.getUsername().equals(holderUsername)) {
                    Account accountByClientID = AccountDAO.getAccountByClientID(clientByID.getId(), connection);
                    IBAN ibanByAccountID = IbanDAO.getIbanByAccountID(accountByClientID.getID(), connection);
                    if (ibanByAccountID.getIBAN() != null && ibanByAccountID.getIBAN().equals(iban)) {
                        long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + ammountOfMoney;
                        AccountDAO.updateAmmountOfMoney(clientByID.getId(), totalAmmountOfMoney, connection);

                        Account accountByClientID1 = AccountDAO.getAccountByClientID(client.getId(), connection);
                        long currentUserAmountMoney = accountByClientID1.getAmountOfMoney() - ammountOfMoney;
                        AccountDAO.updateAmmountOfMoney(client.getId(), currentUserAmountMoney, connection);
                        System.out.println("Transfer completed successfully!");

                        Utils.logEntry("Transferred money to " + clientByID.getId() + ", " +
                                clientByID.getUsername(), client.getId(), connection);
                    } else {
                        System.out.println("No IBAN was found.");
                    }
                } else {
                    System.out.println("No client was found.");
                }
                connection.commit();
            } catch (SQLException e) {
                try {
                    if (connection != null) {
                        connection.rollback();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                throw new RuntimeException("Transaction failed", e);
            } catch (AuditTrailNotSavedException e) {
                System.out.println("Audit Trail not saved.");
            } finally {
                try {
                    if (connection != null) {
                        connection.setAutoCommit(true);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void withdrawMoney(Client client, Connection connection) throws AccountNotFoundException, AuditTrailNotSavedException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = null;
        try {
            accountByClientID = AccountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Account not found.", e);
        }

        System.out.println("How much money do you want to withdraw?");
        int money = readInputInteger();
        long totalAmmountOfMoney;

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            if (accountByClientID.getAmountOfMoney() < money) {
                System.out.println("You don't have that amount in your account.");
                return;
            } else {
                totalAmmountOfMoney = accountByClientID.getAmountOfMoney() - (long) money;
            }

            AccountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney, connection);

            System.out.println("Withdraw successfully!");

            try {
                Utils.logEntry("Withdraw ammount of money: " + money, client.getId(), connection);
            } catch (AuditTrailNotSavedException e) {
                System.out.println("The AT entry is not saved.");
            }

            connection.commit();
        } catch (SQLException e){
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Cannot make a rollback.");
            }
            System.out.println("Rolled back all information, something wrong happened.");
        }
    }

    public void depositMoney(Client client, Connection connection) throws ClientNotFoundException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = null;
        try {
            accountByClientID = AccountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found.", e);
        }

        System.out.println("How much money do you want to deposit?");
        int deposit = readInputInteger();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + (long) deposit;
            AccountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney, connection);

            System.out.println("Money are in account ;)");

            try {
                Utils.logEntry("Deposit ammount of money: " + deposit, client.getId(), connection);
            } catch (AuditTrailNotSavedException e) {
                System.out.println("The AT entry is not saved.");
            }

            connection.commit();
        } catch (SQLException e){
            try {
                connection.rollback();
                System.out.println("Tranzaction failed.");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }

}
