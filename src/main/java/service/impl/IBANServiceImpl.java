package service.impl;


import exceptions.CounterExceededException;
import utils.Utils;

import java.math.BigInteger;
import java.util.*;

public class IBANServiceImpl {
    private static final Set<String> UNIQUE_IBANs = new HashSet<>();
    private final BankServiceImpl bankService = new BankServiceImpl();
    private static Map<String, Integer> MAP_VALUES;

    public String generateIBAN(String bankID) throws CounterExceededException {
        MAP_VALUES = getStringIntegerMap();

        int counter = 0;
        int uniqueCounter = 0;
        while (counter < 5) {
            String countryCode = "RO";
            String checksum = "00";
            String bankCode = bankService.getBankCode(bankID);
            StringBuilder bankIdentificationCode = new StringBuilder(uniqueCounter + Utils.generateNumbers(3));
            StringBuilder uniqueNrOfAccount = new StringBuilder(Utils.generateNumbers(16));

            while(uniqueNrOfAccount.length() < 16){
                uniqueNrOfAccount.insert(0, "0");
            }

            StringBuilder initialIBAN = new StringBuilder();
            initialIBAN.append(bankCode);
            initialIBAN.append(bankIdentificationCode);
            initialIBAN.append(uniqueNrOfAccount);
            initialIBAN.append(countryCode);
            initialIBAN.append(checksum);

            StringBuilder ibanOnlyNumbers = new StringBuilder();

            // for each letter from iban, assigning a number value
            for (char ch : initialIBAN.toString().toCharArray()) {
                getString(MAP_VALUES, ibanOnlyNumbers, ch);
            }
            int reminder = 0;

            // calculate the % of the iban and save the reminder
            BigInteger bigIntegerIbanNumbers = new BigInteger(ibanOnlyNumbers.toString());
            BigInteger mod = bigIntegerIbanNumbers.mod(BigInteger.valueOf(97));
            reminder = Integer.parseInt(String.valueOf(mod));

            String check = "";

            // last check before building the iban
            check = String.valueOf(98 - reminder);
            int checkToInteger = Integer.parseInt(check);
            if (checkToInteger < 10) {
                check = "0" + check;
            }


            int i = checkValidityOfIBANGenerated(bankCode, bankIdentificationCode, uniqueNrOfAccount, countryCode, check);
            if (i == 1) {
                String finalIban = countryCode + check + bankCode + bankIdentificationCode + uniqueNrOfAccount;
                if (UNIQUE_IBANs.add(finalIban)) {
                    return finalIban;
                } else {
                    uniqueCounter++;
                }
            } else {
                counter++;
            }
        }

        if(counter == 5) {
            throw new CounterExceededException(counter);
        } else {
            throw new IllegalArgumentException("Verification of IBAN may be inaccurate. Try again!");
        }
    }


    public int checkValidityOfIBANGenerated(String bankCode, StringBuilder bankIdentificationCode, StringBuilder uniqueNrOfAccount
    , String countryCode, String check) {
        /// Final check if entire iban after checksum is generated % 97 == 1 to ensure validity
        StringBuilder candidateIban = new StringBuilder();
        candidateIban.append(bankCode);
        candidateIban.append(bankIdentificationCode);
        candidateIban.append(uniqueNrOfAccount);
        candidateIban.append(countryCode);
        candidateIban.append(check);
        StringBuilder ibanOnlyNumbersFinalCheck = new StringBuilder();
        for (char ch : candidateIban.toString().toCharArray()) {
            getString(MAP_VALUES, ibanOnlyNumbersFinalCheck, ch);
        }
        BigInteger bigIntegerFinalCheck = new BigInteger(ibanOnlyNumbersFinalCheck.toString());
        BigInteger result = bigIntegerFinalCheck.mod(BigInteger.valueOf(97));
        int i = Integer.parseInt(result.toString());

        return i;
    }

    // convert letters to number. Each letter correspond to a number from MAP_VALUES
    private StringBuilder getString(Map<String, Integer> MAP_VALUES, StringBuilder ibanOnlyNumbers, char ch) {
        String charToString = String.valueOf(ch);
        String upperCase = charToString.toUpperCase();
        Integer i;
        if (upperCase.matches("[A-Z]")) {
            i = MAP_VALUES.get(upperCase);
            ibanOnlyNumbers.append(i);
        } else {
            ibanOnlyNumbers.append(ch);
        }
        return ibanOnlyNumbers;
    }

    // map each letter to corresponded number
    private Map<String, Integer> getStringIntegerMap() {
        final Map<String, Integer> MAP_VALUES = new HashMap<>();
        MAP_VALUES.put("A", 10);
        MAP_VALUES.put("B", 11);
        MAP_VALUES.put("C", 12);
        MAP_VALUES.put("D", 13);
        MAP_VALUES.put("E", 14);
        MAP_VALUES.put("F", 15);
        MAP_VALUES.put("G", 16);
        MAP_VALUES.put("H", 17);
        MAP_VALUES.put("I", 18);
        MAP_VALUES.put("J", 19);
        MAP_VALUES.put("K", 20);
        MAP_VALUES.put("L", 21);
        MAP_VALUES.put("M", 22);
        MAP_VALUES.put("N", 23);
        MAP_VALUES.put("O", 24);
        MAP_VALUES.put("P", 25);
        MAP_VALUES.put("Q", 26);
        MAP_VALUES.put("R", 27);
        MAP_VALUES.put("S", 28);
        MAP_VALUES.put("T", 29);
        MAP_VALUES.put("U", 30);
        MAP_VALUES.put("V", 31);
        MAP_VALUES.put("W", 32);
        MAP_VALUES.put("X", 33);
        MAP_VALUES.put("Y", 34);
        MAP_VALUES.put("Z", 35);
        return MAP_VALUES;
    }


}
