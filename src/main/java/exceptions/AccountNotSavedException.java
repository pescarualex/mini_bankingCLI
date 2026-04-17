package exceptions;

public class AccountNotSavedException extends Exception {
    public AccountNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
