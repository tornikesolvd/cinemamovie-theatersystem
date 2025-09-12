package ticket;

import contract.Bookable;
import contract.Payable;
import showpiece.Showpiece;

import java.math.BigDecimal;
import java.util.Objects;

public final class Ticket extends Showpiece implements Bookable, Payable {

    private static int counter = 1000;

    private final int seatNumber;
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

    public final void printTicketInfo() {
        System.out.println("Ticket{seat=" + seatNumber + ", price=" + price + "}");
    }

    @Override
    public String toString() {
        return "Ticket{seat=" + seatNumber + ", price=" + price + "}";
    }

    @Override
    public boolean isValid() {
        return seatNumber > 0
                && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public String getDisplayname() {
        return "s";
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

    @Override
    public void book() {
        this.occupied = true;
        System.out.println("Booked ticket seat: " + seatNumber);
    }
}
