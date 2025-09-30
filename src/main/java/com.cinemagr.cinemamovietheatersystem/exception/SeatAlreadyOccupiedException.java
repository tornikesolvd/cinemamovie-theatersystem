package com.cinemagr.cinemamovietheatersystem.exception;

public class SeatAlreadyOccupiedException extends RuntimeException {

    private final int seatNumber;

    public SeatAlreadyOccupiedException(int seatNumber) {
        super("Seat " + seatNumber + " is already occupied");
        this.seatNumber = seatNumber;
    }

    public SeatAlreadyOccupiedException(int seatNumber, String message) {
        super(message);
        this.seatNumber = seatNumber;
    }

    public SeatAlreadyOccupiedException(int seatNumber, String message, Throwable cause) {
        super(message, cause);
        this.seatNumber = seatNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }
}


