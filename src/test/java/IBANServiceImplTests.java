import exceptions.CounterExceededException;
import model.Bank;
import org.junit.jupiter.api.Test;
import service.impl.BankServiceImpl;
import service.impl.IBANServiceImpl;


import static org.junit.jupiter.api.Assertions.*;


public class IBANServiceImplTests {

    @Test
    void verifyLengthOfIBANIs28Characters() throws CounterExceededException {
        BankServiceImpl bankService = new BankServiceImpl();
        IBANServiceImpl ibanServiceImpl = new IBANServiceImpl();

        Bank BT = new Bank("BT", "BTRL20", "BTRO", "Mastercard");
        bankService.addBank(BT);

        String iban = ibanServiceImpl.generateIBAN(BT.getID());
        assertEquals(28, iban.length());
    }


}
