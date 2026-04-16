package exceptions;

public class IBANNotSavedException extends Exception{
    public IBANNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
