package utils;


import model.Bank;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Set<String> numbersGenerated = new HashSet<>();
    private static List<String> logs = new ArrayList<>();
    public static List<Bank> banks = new ArrayList<>();

    public static String generateNumbers(int length){
        String generatedString = "";
        String numericString = "1234567890";
        StringBuilder stringBuider = new StringBuilder(length);

        while(true){
            stringBuider.setLength(0);
            for(int i = 0; i < length; i++){
                int index = (int) (numericString.length()
                        * secureRandom.nextDouble());
                stringBuider.append(numericString.charAt(index));
            }
            generatedString = stringBuider.toString();
            if(numbersGenerated.add(generatedString)){
                return generatedString;
            }
        }
    }

    public static void addBank(Bank bank){
        banks.add(bank);
    }

    public static void logEntry(String message){
        logs.add(message);
    }

    public static void getLogs(){
        int counter = 0;
        if(!logs.isEmpty()){
            for(String log : logs){
                System.out.println("System:::: -> " + counter + " - " + log);
                counter++;
            }
        } else {
            System.out.println("System:::: -> No logs");
        }
    }
}
