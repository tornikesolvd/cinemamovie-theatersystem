package com.cinemagr.cinemamovietheatersystem;

import com.cinemagr.cinemamovietheatersystem.annotation.Validate;
import com.cinemagr.cinemamovietheatersystem.cinema.Cinema;
import com.cinemagr.cinemamovietheatersystem.exception.BookingServiceException;
import com.cinemagr.cinemamovietheatersystem.exception.InsufficientFundsException;
import com.cinemagr.cinemamovietheatersystem.exception.InvalidScreeningException;
import com.cinemagr.cinemamovietheatersystem.exception.SeatAlreadyOccupiedException;
import com.cinemagr.cinemamovietheatersystem.exception.CinemaCapacityExceededException;
import com.cinemagr.cinemamovietheatersystem.identity.Customer;
import com.cinemagr.cinemamovietheatersystem.identity.Person;
import com.cinemagr.cinemamovietheatersystem.identity.Staff;
import com.cinemagr.cinemamovietheatersystem.identity.StaffRole;
import com.cinemagr.cinemamovietheatersystem.movie.Movie;
import com.cinemagr.cinemamovietheatersystem.movie.MovieGenre;
import com.cinemagr.cinemamovietheatersystem.movie.MovieInfo;
import com.cinemagr.cinemamovietheatersystem.payment.PaymentMethod;
import com.cinemagr.cinemamovietheatersystem.product.Product;
import com.cinemagr.cinemamovietheatersystem.product.Snack;
import com.cinemagr.cinemamovietheatersystem.product.snackorder.SnackOrder;
import com.cinemagr.cinemamovietheatersystem.resource.CinemaResourceManager;
import com.cinemagr.cinemamovietheatersystem.screening.Screening;
import com.cinemagr.cinemamovietheatersystem.service.BookingService;
import com.cinemagr.cinemamovietheatersystem.theaterhall.TheaterHall;
import com.cinemagr.cinemamovietheatersystem.ticket.Ticket;
import com.cinemagr.cinemamovietheatersystem.ticket.TicketType;
import com.cinemagr.cinemamovietheatersystem.util.Box;
import com.cinemagr.cinemamovietheatersystem.util.Pair;
import com.cinemagr.cinemamovietheatersystem.voucher.Voucher;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MainClass {
    public static void main(String[] args) {
        TheaterHall hall1 = new TheaterHall(1, 3);

        List<TheaterHall> halls = new ArrayList<>();
        halls.add(hall1);
        Cinema cinema = new Cinema("KinoRustaveli", "Tbilisi", halls);

        Staff staff = new Staff("Aza", StaffRole.MANAGER);
        List<Staff> staffList = new ArrayList<>();
        staffList.add(staff);
        cinema.setStaffMembers(staffList);

        Customer customer1 = new Customer("Gio");
        customer1.setEmail("gio@mail.com");
        customer1.setRegistrationDate(LocalDate.of(2024, 5, 10));

        Customer customer2 = new Customer("Toks");
        customer2.setEmail("toks@mail.com");
        customer2.setRegistrationDate(LocalDate.of(2023, 8, 22));

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        cinema.setCustomers(customers);

        Movie movie = new Movie("Tutashkhia", 169);
        movie.setGenre(MovieGenre.DRAMA);

        MovieInfo movieInfo = new MovieInfo(
                "Tutashkhia",
                "Levan Amilakhvari",
                LocalDate.of(2023, 5, 15),
                169,
                new BigDecimal("2500000"),
                "Georgian Film Studio"
        );
        movie.setMovieInfo(movieInfo);

        Screening screening = new Screening();
        screening.setMovie(movie);
        screening.setTime(LocalDateTime.of(2025, 9, 3, 19, 0));
        hall1.addScreening(screening);

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

        List<Snack> snackList = new ArrayList<>();
        snackList.add(popcorn);
        snackList.add(soda);
        SnackOrder order = new SnackOrder(snackList);
        List<SnackOrder> orders1 = new ArrayList<>();
        orders1.add(order);
        customer1.setOrders(orders1);

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

        List<Product> products = new ArrayList<>();
        products.add(voucher);
        cinema.setProducts(products);

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


        Map<Customer, List<Ticket>> customerTickets = new HashMap<>();
        customerTickets.put(customer1, new ArrayList<>());
        customerTickets.get(customer1).add(new Ticket(10, new BigDecimal("11.00")));
        System.out.println("Map size: " + customerTickets.size());
        boolean mapEmpty = customerTickets.isEmpty();
        System.out.println("Map empty: " + mapEmpty);
        if (customers != null && !customers.isEmpty()) {
            Customer firstCustomer = customers.get(0);
            System.out.println("First customer: " + firstCustomer.getName());
        }
        Set<Screening> screenings = hall1.getScreenings();
        if (screenings != null && !screenings.isEmpty()) {
            Screening firstScreening = screenings.iterator().next();
            System.out.println("First screening movie: " + firstScreening.getMovie().getTitle());
        }
        if (!customerTickets.isEmpty()) {
            Map.Entry<Customer, List<Ticket>> firstEntry = customerTickets.entrySet().iterator().next();
            System.out.println("First map key: " + firstEntry.getKey().getName());
        }
        customers.stream()
                .map(Customer::getName)
                .forEach(name -> System.out.println("Iterating list: " + name));

        if (screenings != null) {
            screenings.stream()
                    .map(s -> s.getMovie().getTitle())
                    .forEach(title -> System.out.println("Iterating set: " + title));
        }

        customerTickets.entrySet().stream()
                .map(e -> e.getKey().getName() + " -> " + e.getValue().size())
                .forEach(entry -> System.out.println("Iterating map: " + entry));
        customerTickets.remove(customer1);

        Box<Movie> movieBox = new Box<>(movie);
        System.out.println("Box empty: " + movieBox.empty());
        Pair<String, Integer> pair = new Pair<>("A", 1);
        System.out.println("Pair: " + pair.getKey() + ":" + pair.getValue());


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

        System.out.println("Technical Task: Cleaning Theater Hall");


        cinema.technicalTask(() -> {
            System.out.println("Cleaning projectors");
            System.out.println("Testing sound systems");
        });

        cinema.cleanTheaterHall(hall1, () -> {
            System.out.println("Vacuuming seats");
            System.out.println("Wiping down surfaces");
        });

        System.out.println("Customer action: ");

        cinema.applyCustomerAction((customer) -> {
            System.out.println("Welcome back, " + customer.getName() + "!");
            if (customer.getEmail() != null) {
                System.out.println("Sending promotional email to: " + customer.getEmail());
            }
        });

        System.out.println("Full Movie Info: " + movie.getFullMovieInfo());

        System.out.println("Staff member: " + staff.getDisplayname());
        System.out.println("Role description: " + staff.getRoleDescription());
        System.out.println("Hourly wage: $" + staff.getHourlyWage());

        System.out.println("Cinema status: " + cinema.getStatusMessage());
        System.out.println("Requires staff: " + cinema.requiresStaff());

        Ticket vipTicket = new Ticket(10, new BigDecimal("25.00"), TicketType.VIP, PaymentMethod.CREDIT_CARD);
        System.out.println("VIP Ticket Type Info: " + vipTicket.getTicketTypeInfo());
        System.out.println("Payment Method Info: " + vipTicket.getPaymentMethodInfo());

        System.out.println("Reflection ");

        String staffClassName = "com.cinemagr.cinemamovietheatersystem.identity.Staff";
        try {
            Class<Staff> staffClass = (Class<Staff>) Class.forName(staffClassName);

            for (Field declaredField : staffClass.getDeclaredFields()) {
                System.out.println("Field: " + declaredField.getName());
            }

            for (Method declaredMethod : staffClass.getDeclaredMethods()) {
                System.out.println("Method: " + declaredMethod.getName());
            }

            Constructor<Staff> staffConstructor = staffClass.getDeclaredConstructor(String.class, StaffRole.class);
            Staff staffReflection = staffConstructor.newInstance("Cashier Cashier", StaffRole.CASHIER);
            System.out.println("Created staff using reflection: " + staffReflection);

            Method getHourlyWageMethod = staffClass.getDeclaredMethod("getHourlyWage");
            Object wage = getHourlyWageMethod.invoke(staffReflection);
            System.out.println("getHourlyWage using reflection: " + wage);


            for (Field declaredField : staffClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Validate.class)) {
                    Validate validateAnnotation = declaredField.getAnnotation(Validate.class);
                    System.out.println("Found @Validate annotation on field: " + declaredField.getName() +
                            " with value: " + validateAnnotation.value() +
                            " (required: " + validateAnnotation.required() + ")");
                }
            }

            System.out.println();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("Streaming Operations");

        try (CinemaResourceManager resourceManager = new CinemaResourceManager(cinema)) {

            resourceManager.addCustomer(customer1);
            resourceManager.addCustomer(customer2);
            resourceManager.addStaff(staff);

            System.out.println("All customers names: " + resourceManager.getAllCustomerNames());
            System.out.println("Customers with email: " + resourceManager.getCustomersWithEmail().size());
            System.out.println("Staff by role: " + resourceManager.getStaffByRole(StaffRole.CASHIER).size());
            System.out.println("Customer count by domain: " + resourceManager.getCustomerCountByDomain());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    }
