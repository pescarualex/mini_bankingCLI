package exceptions;

public class AuditTrailNotSavedException extends Exception{
    public AuditTrailNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
