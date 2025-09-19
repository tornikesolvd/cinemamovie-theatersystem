package service;

import contract.Payable;
import identity.Customer;
import product.Product;
import screening.Screening;
import ticket.Ticket;
import exception.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

public class BookingService {

    private static int bookingCounter;

    static {
        bookingCounter = 0;
        System.out.println("BookingService initialized");
    }

    private Payable lastProcessed;

    //during booking, customer is main person who books, he also chooses screening and seat where to sit.
    public final Ticket bookTicket(Customer customer, Screening screening, int seatNumber, BigDecimal price)
            throws BookingServiceException {
        try {
            if (screening == null || screening.getMovie() == null) {
                throw new InvalidScreeningException("Invalid screening or movie.");
            }

            List<Ticket> existingTickets = screening.getTickets();
            if (existingTickets != null && seatNumber <= existingTickets.size() &&
                    existingTickets.get(seatNumber - 1) != null && existingTickets.get(seatNumber - 1).isOccupied()) {
                throw new SeatAlreadyOccupiedException(seatNumber);
            }

            BigDecimal customerBalance = new BigDecimal("50.00"); // Simulated balance
            if (price.compareTo(customerBalance) > 0) {
                throw new InsufficientFundsException(price, customerBalance);
            }

            Ticket ticket = new Ticket(seatNumber, price);
            ticket.setOccupied(true);

            boolean paymentSuccess = ticket.processPayment(price);
            if (!paymentSuccess) {
                throw new BookingServiceException("bookTicket", "Payment processing failed for " + customer.getName());
            }

            if (existingTickets == null) {
                existingTickets = new ArrayList<>();
            }
            int capacity = screening.getMovie().getDuration();
            while (existingTickets.size() < capacity) {
                existingTickets.add(null);
            }
            existingTickets.set(seatNumber - 1, ticket);
            screening.setTickets(existingTickets);

            bookingCounter++;
            System.out.println("Booked seat " + seatNumber + " for " + customer.getName() +
                    " ($" + price + ")");
            return ticket;
        } catch (SeatAlreadyOccupiedException | InvalidScreeningException | InsufficientFundsException e) {

            throw e;
        } catch (Exception e) {
            throw new BookingServiceException("bookTicket", "Unexpected error during booking", e);
        } finally {
            System.out.println("Booking attempt finished for customer: " +
                    (customer != null ? customer.getName() : "Unknown"));
        }
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
