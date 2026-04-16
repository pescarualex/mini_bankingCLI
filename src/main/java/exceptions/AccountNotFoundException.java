package exceptions;

public class AccountNotFoundException extends Exception{
    public AccountNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
