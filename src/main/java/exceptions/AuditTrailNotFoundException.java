package exceptions;

public class AuditTrailNotFoundException extends Exception{
    public AuditTrailNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
