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
            Utils.logEntry("Account created for: " + client.getId(), client.getId(), connection);
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
                if (password != null && clientByID.getPassword().equals(password)) {
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
                        System.out.println("Money: " + accountByClientID.getAmountOfMoney());
                    }

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
                    System.out.println("IBAN: " + ibanByAccountID.getIBAN());
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

        System.out.println("Enter the IBAN:");
        String iban = Utils.readInputString();

        System.out.println("Enter the account holder username: ");
        String holderUsername = Utils.readInputString();

        System.out.println("Amount of money that you want to transfer: ");
        int ammountOfMoney = Utils.readInputInteger();
        Account accountOfCurrentUser;
        try {
            accountOfCurrentUser = accountDAO.getAccountByClientID(client.getId(), connection);
        } catch (SQLException e) {
            throw new AccountNotFoundException("Account not found", e);
        }

        if(accountOfCurrentUser.getAmountOfMoney() < ammountOfMoney){
            System.out.println("You don't have that amount in your account");
            return;
        } else {
            try {
                connection.setAutoCommit(false);
                Client clientByID = clientDAO.getClientByUsername(holderUsername, connection);

                if(clientByID == null){
                    System.out.println("No holder found with this username.");
                } else if (clientByID.getUsername().equals(holderUsername)) {
                    Account accountByClientID = accountDAO.getAccountByClientID(clientByID.getId(), connection);

                    if(accountByClientID == null){
                        System.out.println("Account not found.");
                    } else {
                        IBAN ibanByAccountID = ibanDAO.getIbanByAccountID(accountByClientID.getId(), connection);

                        if(ibanByAccountID == null){
                            System.out.println("IBAN not found.");
                        } else {
                            if (ibanByAccountID.getIBAN() != null && ibanByAccountID.getIBAN().equals(iban)) {
                                long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + ammountOfMoney;
                                accountDAO.updateAmmountOfMoney(clientByID.getId(), totalAmmountOfMoney, connection);

                                Account accountByClientID1 = accountDAO.getAccountByClientID(client.getId(), connection);
                                long currentUserAmountMoney = accountByClientID1.getAmountOfMoney() - ammountOfMoney;
                                accountDAO.updateAmmountOfMoney(client.getId(), currentUserAmountMoney, connection);
                                System.out.println("Transfer completed successfully!");

                                Utils.logEntry("Transferred money to " + clientByID.getId() + ", " +
                                        clientByID.getUsername(), client.getId(), connection);
                            } else {
                                System.out.println("No IBAN was found.");
                            }
                        }
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

            accountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney, connection);

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
        int deposit = readInputInteger();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            long totalAmmountOfMoney = accountByClientID.getAmountOfMoney() + (long) deposit;
            accountDAO.updateAmmountOfMoney(client.getId(), totalAmmountOfMoney, connection);

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
