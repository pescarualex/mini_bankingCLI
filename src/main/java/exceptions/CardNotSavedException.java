package exceptions;

public class CardNotSavedException extends  Exception{
    public CardNotSavedException(String message, Throwable cause){
        super(message, cause);
    }
}
