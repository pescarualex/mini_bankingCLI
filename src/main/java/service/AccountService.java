package service;

import exceptions.AccountNotFoundException;
import exceptions.AccountNotSavedException;
import exceptions.ClientNotFoundException;
import model.Account;
import model.Client;

import java.sql.Connection;

public interface AccountService {
    Account createAccount(Client client, Connection connection) throws AccountNotSavedException;
    void viewAccountDetails(Client client, Connection connection);
    void transferMoney(Client client, Connection connection) throws AccountNotFoundException;
    void withdrawMoney(Client client, Connection connection) throws AccountNotFoundException;
    void depositMoney(Client client, Connection connection) throws ClientNotFoundException;

}
