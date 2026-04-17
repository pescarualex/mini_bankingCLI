package service;

import exceptions.AuditTrailNotSavedException;
import exceptions.BankNotCreatedException;
import model.Bank;

import java.sql.Connection;

public interface BankService {
    Bank createBank(Connection connection) throws AuditTrailNotSavedException, BankNotCreatedException;
    void addBank(Connection connection);
}
