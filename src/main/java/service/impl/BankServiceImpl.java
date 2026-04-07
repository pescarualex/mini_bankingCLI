package service.impl;

import dao.AuditTrailDAO;
import dao.BankDAO;
import model.AuditTrail;
import model.Bank;
import utils.Utils;

import java.sql.SQLException;

public class BankServiceImpl {

    //only and Admin can create banks
    public static Bank createBank() throws SQLException {
        Bank bank = new Bank();
        System.out.println("Bank name:");
        bank.setBankName(Utils.readInputString());
        System.out.println("Bank code: (4 digits)");
        bank.setBankCode(Utils.readInputString());
        System.out.println("Bank swift: ");
        bank.setBankSwift(Utils.readInputString());
        System.out.println("Bank payment network:");
        bank.setPaymentNetwork(Utils.readInputString());

        BankDAO.saveBank(bank);

        System.out.println("Bank created successfully!");

        AuditTrail auditTrail = Utils.logEntry("Bank: " + bank.getBankName() + " was created, and bank ID: " + bank.getID());
        AuditTrailDAO.saveAuditTrail(auditTrail);

        return bank;
    }





}
