package service.impl;

import model.Account;

public class AccountServiceImpl {

    BankServiceImpl bankService = new BankServiceImpl();

    public Account createAccount(String bankID){
        String bankCode = bankService.getBankCode(bankID);
        Account account = new Account(bankCode);
//        account.setCard();
//        account.setIban();
        return account;
    }
}
