package com.cinemagr.cinemamovietheatersystem.service;

import com.cinemagr.cinemamovietheatersystem.contract.Payable;
import com.cinemagr.cinemamovietheatersystem.exception.BookingServiceException;
import com.cinemagr.cinemamovietheatersystem.exception.InsufficientFundsException;
import com.cinemagr.cinemamovietheatersystem.exception.InvalidScreeningException;
import com.cinemagr.cinemamovietheatersystem.exception.SeatAlreadyOccupiedException;
import com.cinemagr.cinemamovietheatersystem.identity.Customer;
import com.cinemagr.cinemamovietheatersystem.product.Product;
import  com.cinemagr.cinemamovietheatersystem.screening.Screening;
import com.cinemagr.cinemamovietheatersystem.ticket.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.function.Function;

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
                    existingTickets.get(seatNumber - 1) != null && existingTickets.get(seatNumber - 1).occupied()) {
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

    // Consumer - Lambda Function
    public void generateTicketAction(Ticket ticket, Consumer<Ticket> action) {
        if (ticket != null && action != null) {
            action.accept(ticket);
        }
    }

    public void notifyTicketBooking(Ticket ticket) {
        generateTicketAction(ticket, t ->
                System.out.println("Notification: Seat " + t.getSeatNumber() +
                        " booked for $" + t.getPrice())
        );
    }

    // BiFunction - Lambda Function
    public BigDecimal calculatePrice(Customer customer, Screening screening, BiFunction<Customer, Screening, BigDecimal> calculator) {
        if (customer == null || screening == null || calculator == null) {
            return BigDecimal.ZERO;
        }
        return calculator.apply(customer, screening);
    }

    public BigDecimal getGeneralPrice(Customer customer, Screening screening) {
        return calculatePrice(customer, screening, (cust, screen) -> new BigDecimal("12.50"));
    }

    // Custom Lambda Expression #3 - Ticket Validation System
    public String validateTicket(Ticket ticket, Function<Ticket, String> validator) {
        if (ticket == null || validator == null) {
            return "Invalid ticket or validator";
        }
        return validator.apply(ticket);
    }

    public String checkTicketValidity(Ticket ticket) {
        return validateTicket(ticket, t -> {
            if (t.getSeatNumber() > 0 && t.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                return "Ticket is valid for seat " + t.getSeatNumber();
            } else {
                return "Ticket is invalid - check seat number and price";
            }
        });
    }

}
