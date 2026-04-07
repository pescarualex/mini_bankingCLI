package utils;

import dao.AuditTrailDAO;
import model.AuditTrail;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Set<String> numbersGenerated = new HashSet<>();
    //private static List<String> logs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static String generateNumbers(int length) {
        String generatedString = "";
        String numericString = "1234567890";
        StringBuilder stringBuider = new StringBuilder(length);

        while (true) {
            stringBuider.setLength(0);
            for (int i = 0; i < length; i++) {
                int index = (int) (numericString.length()
                        * secureRandom.nextDouble());
                stringBuider.append(numericString.charAt(index));
            }

            generatedString = stringBuider.toString();
            if (numbersGenerated.add(generatedString)) {
                return generatedString;
            }
        }
    }


    public static AuditTrail logEntry(String message, int clientID) throws SQLException {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntry(message);
        auditTrail.setTimestamp(LocalDate.now());
        auditTrail.setClientID(clientID);

        AuditTrailDAO.saveAuditTrail(auditTrail);
        return auditTrail;
    }

    public static AuditTrail logEntry(String message) throws SQLException {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntry(message);
        auditTrail.setTimestamp(LocalDate.now());

        AuditTrailDAO.saveAuditTrail(auditTrail);
        return auditTrail;
    }

//    public static void getLogs() {
//        int counter = 0;
//        if (!logs.isEmpty()) {
//            for (String log : logs) {
//                System.out.println(counter + "System -> " + log);
//                counter++;
//            }
//        } else {
//            System.out.println("System -> No logs");
//        }
//    }

    public static String readInputString(){
        return scanner.nextLine();
    }

    public static int readInputInteger(){
        return Integer.parseInt(scanner.nextLine());
    }
}
