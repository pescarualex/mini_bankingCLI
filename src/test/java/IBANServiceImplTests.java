import exceptions.CounterExceededException;
import model.Bank;
import org.junit.jupiter.api.Test;
import service.impl.BankServiceImpl;
import service.impl.IBANServiceImpl;


import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;


public class IBANServiceImplTests {

    static Bank BT = new Bank("BT", "BTRL20", "BTRO", "Mastercard");
    static Bank BCR = new Bank("BCR", "BCRO20", "BCRO", "Mastercard");


    @Test
    void verifyLengthOfIBANIs28Characters() throws CounterExceededException {
        BankServiceImpl bankService = new BankServiceImpl();
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        bankService.addBank(BT);

        String iban = ibanServiceImpl.generateIBAN(BT.getID());
        assertEquals(28, iban.length());
    }


    @Test
    void checkIfIBANHasTheCorrectStructure() throws CounterExceededException {
        BankServiceImpl bankService = new BankServiceImpl();
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        bankService.addBank(BT);

        String iban = ibanServiceImpl.generateIBAN(BT.getID());
        Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{11,30}$");
        Matcher matcher = pattern.matcher(iban);
        boolean b = matcher.matches();
        assertTrue(b);
    }

    @Test
    void verifyIBANGeneratedIsValid() throws CounterExceededException {
        BankServiceImpl bankService = new BankServiceImpl();
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        bankService.addBank(BT);

        String iban = ibanServiceImpl.generateIBAN(BT.getID());
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
        BankServiceImpl bankService = new BankServiceImpl();
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        bankService.addBank(BT);

        String iban1 = ibanServiceImpl.generateIBAN(BT.getID());
        String iban2 = ibanServiceImpl.generateIBAN(BT.getID());

        assertNotEquals(iban1, iban2);
    }


}
