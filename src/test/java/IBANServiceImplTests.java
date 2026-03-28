import exceptions.CounterExceededException;
import model.Bank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.impl.BankServiceImpl;
import service.impl.IBANServiceImpl;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


public class IBANServiceImplTests {
    static Bank BT;
    static Bank BCR;


     @BeforeAll
     public static void initializeBanks(){
         BT = new Bank();
         BT.setID(1);
         BT.setBankName("BT");
         BT.setBankCode("BTRL");
         BT.setPaymentNetwork("Mastercard");
         BT.setBankSwift("BTRO");
         BT.setClientID(17);
         BT.setAccountID(2);

         BCR = new Bank();
         BCR.setID(1);
         BCR.setBankName("BCR");
         BCR.setBankCode("BCRO");
         BCR.setPaymentNetwork("Mastercard");
         BCR.setBankSwift("BCR20");
         BCR.setClientID(17);
         BCR.setAccountID(2);
     }

    @Test
    void verifyLengthOfIBANIs28Characters() throws CounterExceededException {
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        String iban = ibanServiceImpl.generateIBAN("RO", BT);
        assertEquals(28, iban.length());
    }


    @Test
    void checkIfIBANHasTheCorrectStructure() throws CounterExceededException {
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        String iban = ibanServiceImpl.generateIBAN("RO", BT);
        Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$");
        Matcher matcher = pattern.matcher(iban);
        boolean b = matcher.matches();
        assertTrue(b);
    }

    @Test
    void verifyIBANGeneratedIsValid() throws CounterExceededException {
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        String iban = ibanServiceImpl.generateIBAN("RO", BT);
        String[] results = iban.split("(?<=\\G.{" + 4 + "})");
        String countryChecksum = results[0];
        results[0] = "";
        String ibanToCheck = "";
        for(int i = 0; i < results.length; i++){
            ibanToCheck += results[i];
        }
        ibanToCheck += countryChecksum;

        StringBuilder ibanBuilder = new StringBuilder(ibanToCheck);
        int i = ibanServiceImpl.checkValidityOfIBAN(ibanBuilder);
        assertEquals(1, i);
    }

    @Test
    void CheckIfTwoIBANsAreNotEqualsFromTheSameBank() throws CounterExceededException {
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        String iban1 = ibanServiceImpl.generateIBAN("RO", BT);
        String iban2 = ibanServiceImpl.generateIBAN("RO", BT);

        assertNotEquals(iban1, iban2);
    }


}
