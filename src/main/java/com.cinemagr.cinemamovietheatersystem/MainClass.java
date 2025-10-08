package com.cinemagr.cinemamovietheatersystem;

import com.cinemagr.cinemamovietheatersystem.annotation.Validate;
import com.cinemagr.cinemamovietheatersystem.cinema.Cinema;
import com.cinemagr.cinemamovietheatersystem.exception.InsufficientFundsException;
import com.cinemagr.cinemamovietheatersystem.exception.SeatAlreadyOccupiedException;
import com.cinemagr.cinemamovietheatersystem.exception.InvalidScreeningException;
import com.cinemagr.cinemamovietheatersystem.exception.BookingServiceException;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class MainClass {

    private static final Logger LOGGER = LogManager.getLogger(MainClass.class);

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
            LOGGER.info("Successfully booked ticket 1: {}", ticket1);
        } catch (BookingServiceException e) {
            LOGGER.error("Booking failed: {}", e.getMessage());
        } catch (SeatAlreadyOccupiedException e) {
            LOGGER.error("Set Already Occupied: {}", e.getMessage());
        } catch (InvalidScreeningException e) {
            LOGGER.error("Invalid screening: {}", e.getMessage());
        } catch (InsufficientFundsException e) {
            LOGGER.error("Insufficient funds: {}", e.getMessage());
        }

        try {
            Ticket ticket2 = bookingService.bookTicket(customer2, screening, 2, new BigDecimal("15.70"));
            LOGGER.info("Successfully booked ticket 2: {}", ticket2);
        } catch (BookingServiceException e) {
            LOGGER.error("Booking failed: {}", e.getMessage());
        } catch (SeatAlreadyOccupiedException e) {
            LOGGER.error("Seat already occupied: {}", e.getMessage());
        } catch (InvalidScreeningException e) {
            LOGGER.error("Invalid screening: {}", e.getMessage());
        } catch (InsufficientFundsException e) {
            LOGGER.error("Insufficient funds: {}", e.getMessage());
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


        LOGGER.info("Demonstrating try-with-resources ");
        try (CinemaResourceManager resourceManager = new CinemaResourceManager(cinema)) {
            resourceManager.addCustomer(customer1);
            resourceManager.addCustomer(customer2);


            resourceManager.addStaff(staff);


            resourceManager.addProduct(voucher);


            LOGGER.info(resourceManager.getCinemaInfo());
            LOGGER.info("Customer count: " + resourceManager.getCustomerCount());
            LOGGER.info("Staff count:  " + resourceManager.getStaffCount());
            LOGGER.info("Product count: " + resourceManager.getProductCount());


            try {
                cinema.addCustomer(new Customer("Test Customer"));
                LOGGER.info("Added test customer successfully");
            } catch (CinemaCapacityExceededException e) {
                LOGGER.error("Capacity exceeded: {}", e.getMessage());
            }

        } catch (IOException e) {
            LOGGER.error("Error closing resource manager: {}", e.getMessage());
        }

        LOGGER.info(" Resource manager closed automatically");

        List<Product> products = new ArrayList<>();
        products.add(voucher);
        cinema.setProducts(products);

        Ticket directTicket = new Ticket(3, new BigDecimal("15.00"));
        LOGGER.info("Demonstrating processPayment: ");

        bookingService.processPayment(directTicket);

        bookingService.printProductPrice(popcorn);
        bookingService.printProductPrice(voucher);

        LOGGER.info("Cinema: {} ({})", cinema.getName(), cinema.getLocation());
        LOGGER.info("Movie: {} ({})", movie.getTitle(), movie.getGenre());
        LOGGER.info("Hall: {}", hall1.getHallNumber());
        LOGGER.info("Screening: {} at {}", screening.getMovie().getTitle(), screening.getTime());
        LOGGER.info("Staff: {} ({})", staff.getName(), staff.getRole());
        LOGGER.info("Customers: {}, {}", customer1.getName(), customer2.getName());
        LOGGER.info("Generic Person: {}", genericPerson.getName());
        LOGGER.info("Ticket booked: {}, Direct ticket seat {} (${})",
                BookingService.getBookingCounter(), directTicket.getSeatNumber(), directTicket.getPrice());
        LOGGER.info("Voucher: {} (${})", voucher.getName(), voucher.getPrice());
        LOGGER.info("Snacks: {} and {}", popcorn.getName(), soda.getName());
        LOGGER.info("BookingService Counter: {}", BookingService.getBookingCounter());


        Map<Customer, List<Ticket>> customerTickets = new HashMap<>();
        customerTickets.put(customer1, new ArrayList<>());
        customerTickets.get(customer1).add(new Ticket(10, new BigDecimal("11.00")));
        LOGGER.info("Map size: {}", customerTickets.size());
        boolean mapEmpty = customerTickets.isEmpty();
        LOGGER.info("Map empty: {}", mapEmpty);
        if (customers != null && !customers.isEmpty()) {
            Customer firstCustomer = customers.get(0);
            LOGGER.info("First customer: {}", firstCustomer.getName());
        }
        Set<Screening> screenings = hall1.getScreenings();
        if (screenings != null && !screenings.isEmpty()) {
            Screening firstScreening = screenings.iterator().next();
            LOGGER.info("First  screening movie: {}", firstScreening.getMovie().getTitle());
        }
        if (!customerTickets.isEmpty()) {
            Map.Entry<Customer, List<Ticket>> firstEntry = customerTickets.entrySet().iterator().next();
            LOGGER.info("First map value: {}", firstEntry.getValue().get(0).getPrice());
        }
        customers.stream()
                .map(Customer::getName)
                .forEach(name -> LOGGER.info("Iterating list: {}", name));

        if (screenings != null) {
            screenings.stream()
                    .map(s -> s.getMovie().getTitle())
                    .forEach(title -> LOGGER.info("Iterating set: {}", title));
        }

        customerTickets.entrySet().stream()
                .map(e -> e.getKey().getName() + " -> " + e.getValue().size())
                .forEach(entry -> LOGGER.info("Iterating map: {}", entry));
        customerTickets.remove(customer1);

        Box<Movie> movieBox = new Box<>(movie);
        LOGGER.info("Box empty: {}", movieBox.empty());
        Pair<String, Integer> pair = new Pair<>("A", 1);
        LOGGER.info("Pair: {}:{}", pair.getKey(), pair.getValue());


        try {
            bookingService.bookTicket(customer1, screening, 3, new BigDecimal("100.00"));
        } catch (BookingServiceException e) {
            LOGGER.error("Booking failed: {}", e.getMessage());
        } catch (InsufficientFundsException e) {
            LOGGER.error("Insufficient funds: {}", e.getMessage());
        }

        try {
            bookingService.bookTicket(customer2, screening, 1, new BigDecimal("10.00")); // Same seat as ticket1
        } catch (BookingServiceException e) {
            LOGGER.error("Booking failed", e);
        } catch (SeatAlreadyOccupiedException e) {
            LOGGER.error("Seat already occupied", e);
        }


        try {
            bookingService.bookTicket(customer1, null, 4, new BigDecimal("15.00"));
        } catch (BookingServiceException e) {
            LOGGER.error("Booking failed", e);
        } catch (InvalidScreeningException e) {
            LOGGER.error("Invalid screening", e);
        }

        LOGGER.info("Technical Task: Cleaning Theater Hall");

        cinema.technicalTask(() -> {
            LOGGER.info("Cleaning projectors");
            LOGGER.info("Testing sound systems");
        });

        cinema.cleanTheaterHall(hall1, () -> {
            LOGGER.info("Vacuuming seats");
            LOGGER.info("Wiping down surfaces");
        });

        LOGGER.info("Customer action:");

        cinema.applyCustomerAction((customer) -> {
            LOGGER.info("Welcome back, {}!", customer.getName());
            if (customer.getEmail() != null) {
                LOGGER.info("Sending promotional email to: {}", customer.getEmail());
            }
        });

        LOGGER.info("Full Movie Info: {}", movie.getFullMovieInfo());

        LOGGER.info("Staff member: {}", staff.getDisplayname());
        LOGGER.info("Role description: {}", staff.getRoleDescription());
        LOGGER.info("Hourly wage: ${}", staff.getHourlyWage());

        LOGGER.info("Cinema status: {}", cinema.getStatusMessage());
        LOGGER.info("Requires staff: {}", cinema.requiresStaff());

        Ticket vipTicket = new Ticket(10, new BigDecimal("25.00"), TicketType.VIP, PaymentMethod.CREDIT_CARD);
        LOGGER.info("VIP Ticket Type Info: {}", vipTicket.getTicketTypeInfo());
        LOGGER.info("Payment Method Info: {}", vipTicket.getPaymentMethodInfo());


        String staffClassName = "com.cinemagr.cinemamovietheatersystem.identity.Staff";
        try {
            Class<Staff> staffClass = (Class<Staff>) Class.forName(staffClassName);

            for (Field declaredField : staffClass.getDeclaredFields()) {
                LOGGER.info("Field: {}", declaredField.getName());
            }

            for (Method declaredMethod : staffClass.getDeclaredMethods()) {
                LOGGER.info("Method: {}", declaredMethod.getName());
            }

            Constructor<Staff> staffConstructor = staffClass.getDeclaredConstructor(String.class, StaffRole.class);
            Staff staffReflection = staffConstructor.newInstance("Cashier Cashier", StaffRole.CASHIER);
            LOGGER.info("Created staff using reflection: {} ", staffReflection);

            Method getHourlyWageMethod = staffClass.getDeclaredMethod("getHourlyWage");
            Object wage = getHourlyWageMethod.invoke(staffReflection);
            LOGGER.info("getHourlyWage using reflection: {}", wage);


            for (Field declaredField : staffClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Validate.class)) {
                    Validate validateAnnotation = declaredField.getAnnotation(Validate.class);
                    LOGGER.info("Found @Validate annotation on field: {} with value: {} (required: {})",
                            declaredField.getName(),
                            validateAnnotation.value(),
                            validateAnnotation.required());
                }
            }

            LOGGER.info("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("Streaming Operations");

        try (CinemaResourceManager resourceManager = new CinemaResourceManager(cinema)) {

            resourceManager.addCustomer(customer1);
            resourceManager.addCustomer(customer2);
            resourceManager.addStaff(staff);

            LOGGER.info("All customers names: {}", resourceManager.getAllCustomerNames());
            LOGGER.info("Customers with email: {}", resourceManager.getCustomersWithEmail().size());
            LOGGER.info("Staff by role: {}", resourceManager.getStaffByRole(StaffRole.CASHIER).size());
            LOGGER.info("Customer count by domain: {}", resourceManager.getCustomerCountByDomain());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

         /* try {
            String bookFile = FileUtils.readFileToString(new File("src/main/resources/soundOfTale.txt"), StandardCharsets.UTF_8);
            String[] words = StringUtils.split(bookFile, " \n\t.,;:!?()[]{}\"'");
            Set<String> uniqueWordsList = new HashSet<>(Arrays.asList(words));
            FileUtils.writeStringToFile(new File("src/main/resources/uniqueWordsFile.txt"), "Total unique words: " + uniqueWordsList.size(), StandardCharsets.UTF_8);
            LOGGER.info("Total unique words: {}", uniqueWordsList.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
*/

    }
}
