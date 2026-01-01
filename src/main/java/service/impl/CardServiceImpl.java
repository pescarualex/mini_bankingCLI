package service.impl;

import model.Bank;
import model.Card;
import utils.Utils;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

public class CardServiceImpl {

    private static final Set<String> UNIQUE_CARD_NUMBERS = new HashSet<>();
    BankServiceImpl bankService = new BankServiceImpl();


    /// first digit identifies the Visa or Mastercard
    private String generateCardNumber(String bankID){
        StringBuilder initialCardNumber = new StringBuilder();
        StringBuilder cardNumberProcessed = new StringBuilder();

        // add payment network digit
        String paymentNetwork = bankService.getPaymentNetwork(bankID);
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

        // generate the last digit by ceching if the sum is multiple of 10,
        // if not, substract from 10 the reminder of sum
        int checkDigit = 0;
        if((sumOfCharactersFromCardNumber + checkDigit) % 10 != 0){
            checkDigit = 10 - (sumOfCharactersFromCardNumber % 10);
        }

        initialCardNumber.append(checkDigit);

        //check the uniqueness of card number from set list
        if(UNIQUE_CARD_NUMBERS.add(initialCardNumber.toString())){
            return initialCardNumber.toString();
        } else {
            throw new IllegalArgumentException("Invalid card number");
        }
    }


    public static void main(String[] args) {
        Bank BT = new Bank("BT", "BTRL20", "BTRO", "Mastercard");
        BankServiceImpl bankService = new BankServiceImpl();
        bankService.addBank(BT);

        CardServiceImpl cardService = new CardServiceImpl();
        System.out.println(cardService.generateCardNumber(BT.getID()));

    }

    private String generatePinCode(){
        String pin_code = Utils.generateNumbers(4);
        return pin_code;
    }

    private String generateCVV(){
        String CVV = Utils.generateNumbers(3);
        return CVV;
    }

}
