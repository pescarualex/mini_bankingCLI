package service.impl;

import exceptions.CounterExceededException;
import model.Account;

public class AccountServiceImpl {

    BankServiceImpl bankService = new BankServiceImpl();
    //IBANServiceImpl ibanService = new IBANServiceImpl();
    //CardServiceImpl = cardService = new CardServiceImpl();

    public Account createAccount(String bankID) {
        String bankCode = bankService.getBankCode(bankID);
        Account account = new Account(bankCode);
//        account.setCard(cardService.generateCardNumber());
//        account.setIban(ibanService.generateIBAN(bankID));
        return account;
    }
}
