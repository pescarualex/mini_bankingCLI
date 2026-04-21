package service.impl;

import dao.BankDAO;
import exceptions.AuditTrailNotSavedException;
import exceptions.BankNotCreatedException;
import model.Bank;
import service.BankService;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;

public class BankServiceImpl implements BankService {
    private final BankDAO bankDAO;

    public BankServiceImpl(BankDAO bankDAO){
        this.bankDAO = bankDAO;
    }

    //only and Admin can create banks
    @Override
    public Bank createBank(Connection connection) throws AuditTrailNotSavedException, BankNotCreatedException {
        Bank bank = new Bank();
        System.out.println("Bank name:");
        bank.setBankName(Utils.readInputString());
        System.out.println("Bank code: (4 digits)");
        bank.setBankCode(Utils.readInputString());
        System.out.println("Bank swift: ");
        bank.setBankSwift(Utils.readInputString());
        System.out.println("Bank payment network:");
        bank.setPaymentNetwork(Utils.readInputString());

        try {
            bankDAO.saveBank(bank, connection);
        } catch (SQLException e) {
            throw new BankNotCreatedException("Bank not created.", e);
        }

        System.out.println("Bank created successfully!");

        Utils.logEntry("Bank: " + bank.getBankName() + " was created, and bank ID: " + bank.getID(), connection);

        return bank;
    }

    @Override
    public void addBank(Connection connection) {
        try {
            createBank(connection);
        } catch (AuditTrailNotSavedException | BankNotCreatedException e) {
            throw new RuntimeException(e);
        }
    }





}
