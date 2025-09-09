package service;

import identity.Customer;
import product.Product;
import screening.Screening;
import ticket.Ticket;

import java.math.BigDecimal;

public class BookingService {

    private static int bookingCounter;

    static {
        bookingCounter = 0;
        System.out.println("BookingService initialized");
    }

    //during booking, customer is main person who books, he also chooses screening and seat where to sit.
    public Ticket bookTicket(Customer customer, Screening screening, int seatNumber, BigDecimal price) {
        Ticket ticket = new Ticket(seatNumber, new BigDecimal("0.00"));
        ticket.setPrice(new BigDecimal("0.00"));
        ticket.setOccupied(true);

        Ticket[] tickets = screening.getTickets();
        if (tickets == null) {
            tickets = new Ticket[screening.getMovie().getDuration()]; // simplified
        }
        tickets[seatNumber - 1] = ticket;
        screening.setTickets(tickets);

        bookingCounter++;
        System.out.println("Booked seat " + seatNumber + " for " + customer.getName());
        return ticket;
    }

    public static int getBookingCounter() {
        return bookingCounter;
    }

    public void printProductPrice(Product product) {
        System.out.println(product.getName() + " final price: " + product.getFinalPrice());
    }
}
