package exception;


public class BookingServiceException extends Exception {

    private final String operation;

    public BookingServiceException(String operation, String message) {
        super("Booking service error during " + operation + ": " + message);
        this.operation = operation;
    }

    public BookingServiceException(String operation, String message, Throwable cause) {
        super("Booking service error during " + operation + ": " + message, cause);
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}


