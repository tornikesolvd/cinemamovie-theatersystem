package service;

import contract.Payable;
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

    private Payable lastProcessed;

    //during booking, customer is main person who books, he also chooses screening and seat where to sit.
    public final Ticket bookTicket(Customer customer, Screening screening, int seatNumber, BigDecimal price) {
        if (screening == null || screening.getMovie() == null) {
            throw new IllegalArgumentException("Invalid screening or movie.");
        }

        Ticket ticket = new Ticket(seatNumber, price);
        ticket.setOccupied(true);

        boolean paymentSuccess = ticket.processPayment(price);
        if (!paymentSuccess) {
            System.out.println("Payment failed for " + customer.getName() + " at seat " + seatNumber);
            return null;
        }

        Ticket[] tickets = screening.getTickets();
        if (tickets == null) {
            tickets = new Ticket[screening.getMovie().getDuration()];
        }
        tickets[seatNumber - 1] = ticket;
        screening.setTickets(tickets);

        bookingCounter++;
        System.out.println("Booked seat " + seatNumber + " for " + customer.getName() +
                " ($" + price + ")");
        return ticket;
    }

    public void processPayment(Payable payable) {
        if (payable == null) {
            System.out.println("Nothing to process");
            return;
        }
        System.out.println("Processing payment: $" + payable.getPrice());
        this.lastProcessed = payable;
    }


    public static int getBookingCounter() {
        return bookingCounter;
    }

    public void printProductPrice(Product product) {
        System.out.println(product.getName() + " final price: " + product.getFinalPrice());
    }

    public static void resetCounter() {
        bookingCounter = 0;
        System.out.println("Booking counter reset");
    }


}
