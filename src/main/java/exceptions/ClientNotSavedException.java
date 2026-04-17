package exceptions;

public class ClientNotSavedException extends Exception{
    public ClientNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
