package utils;


import java.security.SecureRandom;

public class GenerateInfo {
    static SecureRandom secureRandom = new SecureRandom();

    public static String generateNumbers(int length){
        String generatedString = "";
        int num = 0;
        String numericString = "1234567890";
        StringBuilder stringBuider = new StringBuilder(length);
        for(int i = 1; i <= length; i++){
            int index = (int) (numericString.length()
                    * secureRandom.nextDouble());
            stringBuider.append(numericString.charAt(index));
        }

        generatedString = stringBuider.toString();
        return generatedString;
    }

    public static String generateStrings(int length){
        String generatedString = "";
        String numericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stringBuider = new StringBuilder(length);
        for(int i = 1; i <= length; i++){
            int index = (int) (numericString.length()
                    * secureRandom.nextDouble());
            stringBuider.append(numericString.charAt(index));
        }

        generatedString = stringBuider.toString();
        return generatedString;
    }


}
