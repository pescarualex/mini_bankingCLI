package service.impl;

import dao.BankDAO;
import exceptions.BankNotCreatedException;
import model.Bank;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServiceImpl {

    //only and Admin can create banks
    public static Bank createBank(Connection connection) throws SQLException {
        Bank bank = new Bank();
        System.out.println("Bank name:");
        bank.setBankName(Utils.readInputString());
        System.out.println("Bank code: (4 digits)");
        bank.setBankCode(Utils.readInputString());
        System.out.println("Bank swift: ");
        bank.setBankSwift(Utils.readInputString());
        System.out.println("Bank payment network:");
        bank.setPaymentNetwork(Utils.readInputString());

        BankDAO.saveBank(bank, connection);

        System.out.println("Bank created successfully!");

        Utils.logEntry("Bank: " + bank.getBankName() + " was created, and bank ID: " + bank.getID(), connection);

        return bank;
    }

    public void addBank(Connection connection) throws BankNotCreatedException {
        try {
            BankServiceImpl.createBank(connection);
        } catch (SQLException e) {
            throw new BankNotCreatedException("Bank was not created.", e);
        }
    }





}
