package ticket;

import entity.Entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket extends Entity {

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

    @Override
    public String toString() {
        return "Ticket{seat=" + seatNumber + ", price=" + price + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatNumber, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Ticket ticket)) return false;
        return Objects.equals(seatNumber, ticket.seatNumber)
                && Objects.equals(price, ticket.price);
    }
}
