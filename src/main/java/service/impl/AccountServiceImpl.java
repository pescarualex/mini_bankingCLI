package service.impl;

import exceptions.CounterExceededException;
import model.Card;
import model.IBAN;

public class AccountServiceImpl {

    IBANServiceImpl ibanService = new IBANServiceImpl();
    CardServiceImpl cardService = new CardServiceImpl();

    public void createAccount(String bankID) throws CounterExceededException {
        Card card = new Card();
        card.setCardNumber(cardService.generateCardNumber(bankID));
        card.setPinCode(cardService.generatePinCode());
        card.setExpirationDate(cardService.getCardExpirationDate());
        card.setCVV(cardService.generateCVV());

        IBAN iban = new IBAN();
        iban.setIBAN(ibanService.generateIBAN("RO", bankID));
    }
}
