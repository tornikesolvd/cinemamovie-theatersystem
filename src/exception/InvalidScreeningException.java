package exception;


public class InvalidScreeningException extends RuntimeException {

    public InvalidScreeningException(String message) {
        super(message);
    }

    public InvalidScreeningException(String message, Throwable cause) {
        super(message, cause);
    }
}


