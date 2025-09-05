package ticket;

import java.math.BigDecimal;

public class Ticket {

    private static int counter = 1000;

    private int seatNumber;
    private BigDecimal price;
    private boolean occupied;

    //ticket must have seatnumber and price mentioned.
    public Ticket(int seatNumber, BigDecimal price) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.occupied = false;
        counter++;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public static int getCounter() {
        return counter;
    }
}
