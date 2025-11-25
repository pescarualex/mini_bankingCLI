package service.impl;


import utils.Utils;

import java.util.*;

public class IBANServiceImpl {
    private final Set<String> UNIQUE_IBANs = new HashSet<>();
    static BankServiceImpl bankService = new BankServiceImpl();

    //24 digits
    public static String generateIBAN(String bankID) {
        String iban = "";

        Map<Character, Integer> mapvalues = new HashMap<>();
        mapvalues.put('A', 10);
        mapvalues.put('B', 11);
        mapvalues.put('C', 12);
        mapvalues.put('D', 13);
        mapvalues.put('E', 14);
        mapvalues.put('F', 15);
        mapvalues.put('G', 16);
        mapvalues.put('H', 17);
        mapvalues.put('I', 18);
        mapvalues.put('J', 19);
        mapvalues.put('K', 20);
        mapvalues.put('L', 21);
        mapvalues.put('M', 22);
        mapvalues.put('N', 23);
        mapvalues.put('O', 24);
        mapvalues.put('P', 25);
        mapvalues.put('Q', 26);
        mapvalues.put('R', 27);
        mapvalues.put('S', 28);
        mapvalues.put('T', 29);
        mapvalues.put('U', 30);
        mapvalues.put('V', 31);
        mapvalues.put('W', 32);
        mapvalues.put('X', 33);
        mapvalues.put('Y', 34);
        mapvalues.put('Z', 35);

        String countryCode = "RO";
        String checksum = Utils.generateNumbers(2);
        String bankCode = "BTRO"; //bankService.getBankCode(bankID);
        String counterToString = Utils.generateNumbers(4);
        String uniqueNrOfAccount = Utils.generateNumbers(12);

        iban = countryCode + checksum + bankCode + counterToString + uniqueNrOfAccount;
        String ibanForModAlg = bankCode + counterToString + uniqueNrOfAccount + countryCode + checksum;

        char[] ibanToChar = ibanForModAlg.toCharArray();

        String ibanOnlyNumbers = "";

        List<Integer> listOfNumbersFromLetters = new ArrayList<>();


        for (char ch : ibanToChar) {
            String s = String.valueOf(ch);
            Integer i;
            if (s.matches("[A-Z]")) {
                i = mapvalues.get(ch);
                ibanOnlyNumbers = ibanOnlyNumbers + i;
                listOfNumbersFromLetters.add(i);
            } else {
                ibanOnlyNumbers = ibanOnlyNumbers + ch;

            }
        }






        return iban;
    }


    public static void main(String[] args) {
//        char ch = 'b';
//        int pos = ch - 'a' + 10;
//        System.out.println(pos);
        System.out.println(generateIBAN("BTRO"));

    }


}
