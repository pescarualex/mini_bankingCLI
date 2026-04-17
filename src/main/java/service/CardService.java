package service;

import exceptions.CardNotSavedException;
import model.Account;
import model.Bank;
import model.Card;

import java.sql.Connection;
import java.time.LocalDate;

public interface CardService {
    Card createCard(Bank bank, Account account, Connection connection) throws CardNotSavedException;
    String generateCardNumber(Bank bank);
    String generatePinCode();
    String generateCVV();
    LocalDate getCardExpirationDate();
}
