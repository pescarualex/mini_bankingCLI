package service.impl;

import dao.AuditTrailDAO;
import dao.BankDAO;
import model.Account;
import model.AuditTrail;
import model.Bank;
import model.Client;
import utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl {

    public Bank createBank(Client client, Account account) throws SQLException {
        Bank bank = new Bank();
        bank.setBankName(Utils.readInputString());
        bank.setBankCode(Utils.readInputString());
        bank.setBankSwift(Utils.readInputString());
        bank.setPaymentNetwork(Utils.readInputString());
        bank.setAccountID(account.getID());
        bank.setClientID(client.getId());

        BankDAO.saveBank(bank);

        AuditTrail auditTrail = Utils.logEntry("Created bank: " + bank.getBankName() + ", accountID: " + account.getID(), client.getId());
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return bank;
    }





}
