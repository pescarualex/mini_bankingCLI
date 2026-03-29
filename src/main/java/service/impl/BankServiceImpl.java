package service.impl;

import dao.BankDAO;
import model.Account;
import model.Bank;
import model.Client;
import utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankServiceImpl {

    public Bank createBank(Client client, Account account) throws SQLException {
        Bank bank = new Bank();
        bank.setBankName(Utils.readInput());
        bank.setBankCode(Utils.readInput());
        bank.setBankSwift(Utils.readInput());
        bank.setPaymentNetwork(Utils.readInput());
        bank.setAccountID(account.getID());
        bank.setClientID(client.getId());

        BankDAO.saveBank(bank);

        return bank;
    }





}
