package service.impl;

import dao.AccountDAO;
import dao.AuditTrailDAO;
import model.Account;
import model.AuditTrail;
import model.Client;
import utils.Utils;

import java.sql.SQLException;

public class AccountServiceImpl {

    public static Account createAccount(Client client) throws SQLException {
        Account account = new Account();
        account.setAmountOfMoney(0L);
        account.setClient_ID(client.getId());

        AccountDAO.saveAccount(account);
        AuditTrail auditTrail = Utils.logEntry("Account created for: " + client.getId(), client.getId());
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return account;
    }

}
