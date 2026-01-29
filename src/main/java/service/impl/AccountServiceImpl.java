package service.impl;

import exceptions.CounterExceededException;
import model.Account;
import model.Card;
import model.IBAN;

public class AccountServiceImpl {

    BankServiceImpl bankService = new BankServiceImpl();
    IBANServiceImpl ibanService = new IBANServiceImpl();
    CardServiceImpl cardService = new CardServiceImpl();

    public Account createAccount(String bankID) throws CounterExceededException {
        String bankCode = bankService.getBankCode(bankID);


        Card card = new Card();
        card.setCardNumber(cardService.generateCardNumber(bankID));
        card.setPinCode(cardService.generatePinCode());
        card.setExpirationDate(cardService.getCardExpirationDate());
        card.setCVV(cardService.generateCVV());

        IBAN iban = new IBAN();
        iban.setIBAN(ibanService.generateIBAN("RO", bankID));


        Account account = new Account(bankCode);
        account.setCard(card);
        account.setIban(iban);
        return account;
    }
}
