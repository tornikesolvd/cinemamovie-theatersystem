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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
        Ticket ticket1 = bookingService.bookTicket(customer1, screening, 1, new BigDecimal("12.50"));
        Ticket ticket2 = bookingService.bookTicket(customer2, screening, 2, new BigDecimal("15.70"));

        Snack popcorn = new Snack("Popcorn");
        popcorn.setPrice(new BigDecimal("5.00"));

        Snack soda = new Snack("Soda");
        soda.setPrice(new BigDecimal("3.00"));

        SnackOrder order = new SnackOrder(new Snack[]{popcorn, soda});
        customer1.setOrders(new SnackOrder[]{order});

        Person genericPerson = new Staff("Ucnobi Person", "Unknown role"); // using Person abstract super class
        genericPerson.setEmail("ucnobi@mail.com");

        Product voucher = new Voucher("Gift Voucher");
        voucher.setPrice(new BigDecimal("20.00"));

        Ticket directTicket = new Ticket(3, new BigDecimal("15.00"));

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
    }
}

