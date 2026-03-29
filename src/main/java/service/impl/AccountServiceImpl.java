package service.impl;

import dao.AccountDAO;
import model.Account;
import model.Client;

import java.sql.SQLException;

public class AccountServiceImpl {

    public Account createAccount(Client client) throws SQLException {
        Account account = new Account();
        account.setAmountOfMoney(0L);
        account.setClient_ID(client.getId());

        AccountDAO.saveAccount(account);

        return account;
    }

}
