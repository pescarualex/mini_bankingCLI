package exceptions;

public class BankNotCreatedException extends Exception{
    public BankNotCreatedException(String message, Throwable cause){
        super(message, cause);
    }
}
