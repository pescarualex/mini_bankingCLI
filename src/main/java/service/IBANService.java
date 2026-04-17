package service;

import exceptions.AuditTrailNotSavedException;
import exceptions.CounterExceededException;
import exceptions.IBANNotSavedException;
import model.Account;
import model.Bank;
import model.IBAN;

import java.sql.Connection;
import java.util.Map;

public interface IBANService {
    IBAN createIban(String countryCode, Bank bank, Account account, Connection connection) throws
            CounterExceededException, IBANNotSavedException, AuditTrailNotSavedException;
    String generateIBAN(String countryCode, Bank bank) throws CounterExceededException;
    int checkValidityOfIBAN(StringBuilder stringBuilder);
    StringBuilder convertLetterToNumber(Map<String,Integer> map, StringBuilder stringBuilder, char ch);
    Map<String, Integer> getStringIntegerMap();
}
