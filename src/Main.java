import cinema.Cinema;
import identity.Customer;
import identity.Person;
import identity.Staff;
import movie.Movie;
import product.Product;
import product.Snack;
import product.snackorder.SnackOrder;
import screening.Screening;
import service.BookingService;
import theaterhall.TheaterHall;
import ticket.Ticket;
import voucher.Voucher;
import resource.CinemaResourceManager;
import exception.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        TheaterHall hall1 = new TheaterHall(1, 3);

        Cinema cinema = new Cinema("KinoRustaveli", "Tbilisi", new TheaterHall[]{hall1});

        Staff staff = new Staff("Aza", "Servant");
        cinema.setStaffMembers(new Staff[]{staff});

        Customer customer1 = new Customer("Gio");
        customer1.setEmail("gio@mail.com");
        customer1.setRegistrationDate(LocalDate.of(2024, 5, 10));

        Customer customer2 = new Customer("Toks");
        customer2.setEmail("toks@mail.com");
        customer2.setRegistrationDate(LocalDate.of(2023, 8, 22));

        cinema.setCustomers(new Customer[]{customer1, customer2});

        Movie movie = new Movie("Tutashkhia", 169);
        movie.setGenre("Drama");

        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setTime(LocalDateTime.of(2025, 9, 3, 19, 0));
        hall1.setScreenings(new Screening[]{screening});

        BookingService bookingService = new BookingService();

        // right here is demonstrated exception handling with try-catch
        try {
            Ticket ticket1 = bookingService.bookTicket(customer1, screening, 1, new BigDecimal("12.50"));
            System.out.println("Successfully booked ticket 1: " + ticket1);
        } catch (BookingServiceException e) {
            System.err.println("Booking failed: " + e.getMessage());
        } catch (SeatAlreadyOccupiedException e) {
            System.err.println("Seat already occupied: " + e.getMessage());
        } catch (InvalidScreeningException e) {
            System.err.println("Invalid screening: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Insufficient funds: " + e.getMessage());
        }

        try {
            Ticket ticket2 = bookingService.bookTicket(customer2, screening, 2, new BigDecimal("15.70"));
            System.out.println("Successfully booked ticket 2: " + ticket2);
        } catch (BookingServiceException e) {
            System.err.println("Booking failed: " + e.getMessage());
        } catch (SeatAlreadyOccupiedException e) {
            System.err.println("Seat already occupied: " + e.getMessage());
        } catch (InvalidScreeningException e) {
            System.err.println("Invalid screening: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Insufficient funds: " + e.getMessage());
        }

        Snack popcorn = new Snack("Popcorn");
        popcorn.setPrice(new BigDecimal("5.00"));

        Snack soda = new Snack("Soda");
        soda.setPrice(new BigDecimal("3.00"));

        SnackOrder order = new SnackOrder(new Snack[]{popcorn, soda});
        customer1.setOrders(new SnackOrder[]{order});

        Person genericPerson = new Staff("Ucnobi Person", "Unknown role");
        genericPerson.setEmail("ucnobi@mail.com");

        Product voucher = new Voucher("Gift Voucher");
        voucher.setPrice(new BigDecimal("20.00"));


        System.out.println(" Demonstrating try-with-resources ");
        try (CinemaResourceManager resourceManager = new CinemaResourceManager(cinema)) {
            resourceManager.addCustomer(customer1);
            resourceManager.addCustomer(customer2);


            resourceManager.addStaff(staff);


            resourceManager.addProduct(voucher);


            System.out.println(resourceManager.getCinemaInfo());
            System.out.println("Customer count: " + resourceManager.getCustomerCount());
            System.out.println("Staff count: " + resourceManager.getStaffCount());
            System.out.println("Product count: " + resourceManager.getProductCount());


            try {
                cinema.addCustomer(new Customer("Test Customer"));
                System.out.println("Added test customer successfully");
            } catch (CinemaCapacityExceededException e) {
                System.err.println("Capacity exceeded: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("Error closing resource manager: " + e.getMessage());
        }

        System.out.println(" Resource manager closed automatically ===");

        cinema.setProducts(new Product[]{voucher});

        Ticket directTicket = new Ticket(3, new BigDecimal("15.00"));
        System.out.println("Demonstrating processPayment: ");

        bookingService.processPayment(directTicket);

        bookingService.printProductPrice(popcorn);
        bookingService.printProductPrice(voucher);

        System.out.println("Cinema: " + cinema.getName() + " (" + cinema.getLocation() + ")");
        System.out.println("Movie: " + movie.getTitle() + " (" + movie.getGenre() + ")");
        System.out.println("Hall: " + hall1.getHallNumber());
        System.out.println("Screening: " + screening.getMovie().getTitle() + " at " + screening.getTime());
        System.out.println("Staff: " + staff.getName() + " (" + staff.getRole() + ")");
        System.out.println("Customers: " + customer1.getName() + ", " + customer2.getName());
        System.out.println("Generic Person: " + genericPerson.getName());
        System.out.println("Ticket booked: " + BookingService.getBookingCounter() +
                ", Direct ticket seat " + directTicket.getSeatNumber() +
                " ($" + directTicket.getPrice() + ")");
        System.out.println("Voucher: " + voucher.getName() + " ($" + voucher.getPrice() + ")");
        System.out.println("Snacks: " + popcorn.getName() + " and " + soda.getName());
        System.out.println("BookingService Counter: " + BookingService.getBookingCounter());


        System.out.println(" Additional Exception Demonstrations ");

        try {
            bookingService.bookTicket(customer1, screening, 3, new BigDecimal("100.00"));
        } catch (BookingServiceException e) {
            System.err.println("Booking failed: " + e.getMessage());
        } catch (InsufficientFundsException e) {
            System.err.println("Insufficient funds: " + e.getMessage());
        }

        try {
            bookingService.bookTicket(customer2, screening, 1, new BigDecimal("10.00")); // Same seat as ticket1
        } catch (BookingServiceException e) {
            System.err.println("Booking failed: " + e.getMessage());
        } catch (SeatAlreadyOccupiedException e) {
            System.err.println("Seat already occupied: " + e.getMessage());
        }


        try {
            bookingService.bookTicket(customer1, null, 4, new BigDecimal("15.00"));
        } catch (BookingServiceException e) {
            System.err.println("Booking failed: " + e.getMessage());
        } catch (InvalidScreeningException e) {
            System.err.println("Invalid screening: " + e.getMessage());
        }

        System.out.println(" All demonstrations completed ");
    }
}



