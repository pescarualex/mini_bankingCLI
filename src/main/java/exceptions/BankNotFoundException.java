package exceptions;

public class BankNotFoundException extends Exception{
    public BankNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
