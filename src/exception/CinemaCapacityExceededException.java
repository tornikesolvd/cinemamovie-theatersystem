package exception;

public class CinemaCapacityExceededException extends RuntimeException {

    private final int currentCapacity;
    private final int maxCapacity;

    public CinemaCapacityExceededException(int currentCapacity, int maxCapacity) {
        super("Cinema capacity exceeded. Current: " + currentCapacity + ", Max: " + maxCapacity);
        this.currentCapacity = currentCapacity;
        this.maxCapacity = maxCapacity;
    }

    public CinemaCapacityExceededException(String message, int currentCapacity, int maxCapacity) {
        super(message);
        this.currentCapacity = currentCapacity;
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}

