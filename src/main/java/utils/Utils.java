package utils;

import dao.AuditTrailDAO;
import exceptions.AuditTrailNotSavedException;
import model.AuditTrail;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Utils {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Set<String> numbersGenerated = new HashSet<>();
    //private static List<String> logs = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    static AuditTrailDAO auditTrailDAO = new AuditTrailDAO();

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


    public static AuditTrail logEntry(String message, int clientID, Connection connection) throws AuditTrailNotSavedException {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntry(message);
        auditTrail.setTimestamp(LocalDateTime.now());
        auditTrail.setClientId(clientID);

        try {
            auditTrailDAO.saveAuditTrail(auditTrail, connection);
        } catch (SQLException e) {
            throw new AuditTrailNotSavedException("Audit Trail not saved.", e);
        }
        return auditTrail;
    }

    public static AuditTrail logEntry(String message, Connection connection) throws AuditTrailNotSavedException {
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setEntry(message);
        auditTrail.setTimestamp(LocalDateTime.now());

        try {
            auditTrailDAO.saveAuditTrail(auditTrail, connection);
        } catch (SQLException e) {
            throw new AuditTrailNotSavedException("Audit Trail not saved.", e);
        }
        return auditTrail;
    }

    public static String readInputString(){
        return scanner.nextLine();
    }

    public static int readInputInteger(){
        return Integer.parseInt(scanner.nextLine());
    }
}
