package exceptions;

public class AdminNotFoundException extends Exception{
    public AdminNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
