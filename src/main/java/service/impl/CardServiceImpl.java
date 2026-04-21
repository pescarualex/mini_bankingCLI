package service.impl;

import dao.CardDAO;
import exceptions.AuditTrailNotSavedException;
import exceptions.CardNotSavedException;
import model.Account;
import model.Bank;
import model.Card;
import service.CardService;
import utils.Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class CardServiceImpl implements CardService {

    private final CardDAO cardDAO;

    public CardServiceImpl(CardDAO cardDAO){
        this.cardDAO = cardDAO;
    }

    @Override
    public Card createCard(Bank bank, Account account, Connection connection) throws CardNotSavedException {
        Card card = new Card();
        card.setCardNumber(generateCardNumber(bank));
        card.setPinCode(generatePinCode());
        card.setCVV(generateCVV());
        card.setExpirationDate(getCardExpirationDate());
        card.setAccountId(account.getId());

        try {
            cardDAO.saveCard(card, connection);
        } catch (SQLException e) {
            throw new CardNotSavedException("Card not saved.", e);
        }

        try {
            Utils.logEntry("Created card for account: " + account.getId() +
                    " at bank: " + bank.getBankName(), account.getClientId(), connection);
        } catch (AuditTrailNotSavedException e) {
            System.out.println("Audit trail entry not saved for card creation.");
        }

        return card;
    }


    /// first digit identifies the Visa or Mastercard
    @Override
    public String generateCardNumber(Bank bank){
        StringBuilder initialCardNumber = new StringBuilder();
        StringBuilder cardNumberProcessed = new StringBuilder();

        // add payment network digit
        String paymentNetwork = bank.getPaymentNetwork();
        String paymentNetworkDigit = "";
        if(paymentNetwork.equals("Visa")){
            paymentNetworkDigit = "4";
        } else if(paymentNetwork.equals("Mastercard")){
            paymentNetworkDigit = "5";
        }

        initialCardNumber.append(paymentNetworkDigit);
        initialCardNumber.append(Utils.generateNumbers(14));

        //from the right of the array, double the second number and if is > than 9, substract 9
        char [] charactersOfCardNumber = initialCardNumber.toString().toCharArray();
        int distance = 0;
        for(int i = charactersOfCardNumber.length - 1; i >= 0; i--){
            distance = (charactersOfCardNumber.length - 1) - i;
            if(distance % 2 == 1){
                int num = Integer.parseInt(String.valueOf(charactersOfCardNumber[i]));
                num *= 2;
                if(num > 9){
                    num -= 9;
                    String i1ToString = String.valueOf(num);
                    charactersOfCardNumber[i] = i1ToString.charAt(0);
                }
            }
        }

        //append all numbers from array to card number
        for(int i = 0; i < charactersOfCardNumber.length; i++){
            cardNumberProcessed.append(charactersOfCardNumber[i]);
        }

        // add all numbers into a sum
        int sumOfCharactersFromCardNumber = 0;
        char[] charactersAfterProcessing = cardNumberProcessed.toString().toCharArray();
        for(int i = 0; i < charactersAfterProcessing.length; i++){
            String s = String.valueOf(charactersAfterProcessing[i]);
            int i1 = Integer.parseInt(s);
            sumOfCharactersFromCardNumber += i1;
        }

        // generate the last digit by checking if the sum is multiple of 10,
        // if not, subtract from 10 the reminder of sum
        int checkDigit = 0;
        if((sumOfCharactersFromCardNumber + checkDigit) % 10 != 0){
            checkDigit = 10 - (sumOfCharactersFromCardNumber % 10);
        }

        StringBuilder append = initialCardNumber.append(checkDigit);
        return append.toString();
    }

    @Override
    public String generatePinCode(){
        return Utils.generateNumbers(4);
    }

    @Override
    public String generateCVV(){
        return Utils.generateNumbers(3);
    }

    @Override
    public LocalDate getCardExpirationDate(){
        LocalDate date = LocalDate.now();
        return date.plusYears(5L);
    }

}
