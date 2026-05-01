package service.impl;

import dao.AccountDAO;
import dao.CardDAO;
import dao.ClientDAO;
import dao.IbanDAO;
import enums.Status;
import exceptions.AccountNotFoundException;
import exceptions.AccountNotSavedException;
import exceptions.AuditTrailNotSavedException;
import exceptions.ClientNotFoundException;
import model.Account;
import model.Card;
import model.Client;
import model.IBAN;
import service.AccountService;
import utils.PasswordUtils;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import static utils.Utils.readInputInteger;

public class AccountServiceImpl implements AccountService {

    private final AccountDAO accountDAO;
    private final ClientDAO clientDAO;
    private final CardDAO cardDAO;
    private final IbanDAO ibanDAO;

    public AccountServiceImpl(AccountDAO accountDAO, ClientDAO clientDAO, CardDAO cardDAO, IbanDAO ibanDAO){
        this.accountDAO = accountDAO;
        this.clientDAO = clientDAO;
        this.cardDAO = cardDAO;
        this.ibanDAO = ibanDAO;
    }

    @Override
    public Account createAccount(Client client, Connection connection) throws AccountNotSavedException {
        Account account = new Account();
        account.setAmountOfMoney(0L);
        account.setClientId(client.getId());

        try {
            accountDAO.saveAccount(account, connection);
        } catch (SQLException e) {
            throw new AccountNotSavedException("Account not saved.", e);
        }

        try {
            Utils.logEntry("Created account with id: " + account.getId() + " for: " + client.getId(), client.getId(), connection);
        } catch (AuditTrailNotSavedException e) {
            System.out.println("Audit trail entry not saved.");
        }
        return account;
    }

    @Override
    public void viewAccountDetails(Client client, Connection connection) {
        System.out.print("To proceed, you need to confirm your password\n" +
                "->");
        String password = Utils.readInputString();

        try {
            Client clientByID = clientDAO.getClientByID(client.getId(), connection);

            if(clientByID == null){
                System.out.println("Client not found.");
            } else {
                if (PasswordUtils.verifyPassword(password, clientByID.getPassword())) {
                    System.out.println("Client Information:");
                    System.out.println("First name: " + clientByID.getFirstName() + ", Last name: " + clientByID.getLastName() + "\n" +
                            "CNP: " + clientByID.getCNP() + ", Series and CI Number: " + clientByID.getSeriesAndNumberOfCI() + "\n" +
                            "Username: " + clientByID.getUsername() + ", Role: " + clientByID.getRole().name() + ", Status: " +
                            clientByID.getStatus().name() + "\n" +
                            "Bank ID: " + clientByID.getBankID());

                    Account accountByClientID = accountDAO.getAccountByClientID(clientByID.getId(), connection);
                    if(accountByClientID == null){
                        System.out.println("Account not found.");
                    } else {
                        System.out.println("Account information:");
                        System.out.println("Amount of Money: " + accountByClientID.getAmountOfMoney());

                        System.out.println("Card information: ");
                        Card cardByAccountID = cardDAO.getCardByAccountID(accountByClientID.getId(), connection);
                        if(cardByAccountID == null){
                            System.out.println("Card not found.");
                        } else {
                            System.out.println("Card Number: " + cardByAccountID.getCardNumber() + ", Card Expiration Period: " +
                                    cardByAccountID.getExpirationDate() + "\n" +
                                    "Card Pin Code: " + cardByAccountID.getPinCode() + ", Card CVV: " + cardByAccountID.getCVV());
                        }

                        System.out.println("IBAN information: ");
                        IBAN ibanByAccountID = ibanDAO.getIbanByAccountID(accountByClientID.getId(), connection);
                        if(ibanByAccountID == null){
                            System.out.println("No IBAN found.");
                        } else {
                            System.out.println("IBAN: " + ibanByAccountID.getIBAN());
                        }
                    }
                }
            }
        } catch (SQLException e){
            System.out.println("Account details not available this time.");
        }
    }

    @Override
    public void transferMoney(Client client, Connection connection) throws AccountNotFoundException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        System.out.println("Enter the holder IBAN:");
        String iban = Utils.readInputString();


        System.out.println("Enter the holder username: ");
        String holderUsername = Utils.readInputString();

        System.out.println("Amount of money that you want to transfer: ");
        long amountOfMoney = Utils.readInputInteger();

        Account accountOfCurrentUser;
        try {
            accountOfCurrentUser = accountDAO.getAccountByClientID(client.getId(), connection);
            IBAN ibanOfCurrentUser = ibanDAO.getIbanByAccountID(accountOfCurrentUser.getId(), connection);

            if(ibanOfCurrentUser.getIBAN().equals(iban) || client.getUsername().equals(holderUsername)){
                System.out.println("You can't transfer money into your account.");
                throw new IllegalArgumentException("Transfer failed.");
            }
        } catch (SQLException e) {
            throw new AccountNotFoundException("Account not found", e);
        }

        if(accountOfCurrentUser.getAmountOfMoney() < amountOfMoney){
            System.out.println("You don't have that amount in your account");
        } else {
            try {
                connection.setAutoCommit(false);
                Client holder = clientDAO.getClientByUsername(holderUsername, connection);

                if(holder == null){
                    System.out.println("No holder found.");
                } else if (holder.getUsername().equals(holderUsername)) {
                    Account holderAccount = accountDAO.getAccountByClientID(holder.getId(), connection);

                    if(holderAccount == null){
                        System.out.println("Account not found.");
                    } else {
                        IBAN ibanHolder = ibanDAO.getIbanByAccountID(holderAccount.getId(), connection);

                        if(ibanHolder == null){
                            System.out.println("IBAN not found.");
                        } else {
                            if (ibanHolder.getIBAN() != null && ibanHolder.getIBAN().equals(iban)) {
                                holderAccount.setAmountOfMoney(holderAccount.getAmountOfMoney() + amountOfMoney);
                                accountDAO.updateAmountOfMoney(holder.getId(), holderAccount.getAmountOfMoney(), connection);

                                accountOfCurrentUser.setAmountOfMoney(accountOfCurrentUser.getAmountOfMoney() - amountOfMoney);
                                accountDAO.updateAmountOfMoney(client.getId(), accountOfCurrentUser.getAmountOfMoney(), connection);
                                System.out.println("Transfer completed successfully!");

                                Utils.logEntry("User with id: " + client.getId() + " transferred money to " + holder.getId() + ", " +
                                        holder.getUsername(), client.getId(), connection);
                            } else {
                                System.out.println("IBAN not found.");
                            }
                        }
                    }
                } else {
                    System.out.println("Client not found.");
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

    @Override
    public void withdrawMoney(Client client, Connection connection) throws AccountNotFoundException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = null;
        try {
            accountByClientID = accountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Account not found.", e);
        }


        System.out.println("How much money do you want to withdraw?");
        long money = readInputInteger();

        if(money <= 0){
            throw new IllegalArgumentException("Negative sold not allowed.");
        } else {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {

                if(accountByClientID == null){
                    System.out.println("Account not found.");
                } else {
                    if (accountByClientID.getAmountOfMoney() < money) {
                        System.out.println("You don't have that amount in your account.");
                        return;
                    } else {
                        accountByClientID.setAmountOfMoney(accountByClientID.getAmountOfMoney() - money);
                    }
                }

                accountDAO.updateAmountOfMoney(client.getId(), accountByClientID.getAmountOfMoney(), connection);

                System.out.println("Withdraw successfully!");

                try {
                    Utils.logEntry("Withdraw amount of money: " + money, client.getId(), connection);
                } catch (AuditTrailNotSavedException e) {
                    System.out.println("Audit Trail not saved.");
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
    }

    @Override
    public void depositMoney(Client client, Connection connection) throws ClientNotFoundException {
        if(client.getStatus().equals(Status.PENDING)){
            System.out.println("You are not approved yet. Please try later.");
            return;
        }

        Account accountByClientID = null;
        try {
            accountByClientID = accountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new ClientNotFoundException("Client not found.", e);
        }

        System.out.println("How much money do you want to deposit?");
        long deposit = readInputInteger();

        if(deposit <= 0){
            throw new IllegalArgumentException("Negative sold not allowed.");
        } else {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if(accountByClientID == null){
                    System.out.println("Account not found.");
                } else {
                    accountByClientID.setAmountOfMoney(accountByClientID.getAmountOfMoney() + (long) deposit);
                    accountDAO.updateAmountOfMoney(client.getId(), accountByClientID.getAmountOfMoney(), connection);

                    System.out.println("Money are in account ;)");
                }

                try {
                    Utils.logEntry("Deposit amount of money: " + deposit, client.getId(), connection);
                } catch (AuditTrailNotSavedException e) {
                    System.out.println("Audit Trail not saved.");
                }

                connection.commit();
            } catch (SQLException e){
                try {
                    connection.rollback();
                    System.out.println("Transaction failed.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }
    }

}
