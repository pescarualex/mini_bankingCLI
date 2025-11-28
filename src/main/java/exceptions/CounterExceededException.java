package exceptions;

public class CounterExceededException extends Exception{
    public CounterExceededException(int counter) {
        super("Counter exceeded with " + counter + " iterations. " +
                "No generation of IBAN was valid. Try again!");
    }
}
