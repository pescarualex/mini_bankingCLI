package exceptions;

public class ConnectionNotFoundException extends Exception{
    public ConnectionNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
