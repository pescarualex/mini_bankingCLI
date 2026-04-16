package exceptions;

public class ClientNotUpdatedException extends Exception{
    public ClientNotUpdatedException(String message, Throwable cause){
        super(message, cause);
    }
}
