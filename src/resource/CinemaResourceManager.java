package resource;

import cinema.Cinema;
import identity.Customer;
import identity.Staff;
import product.Product;
import product.Snack;


import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.Consumer;

public class CinemaResourceManager implements AutoCloseable {

    private final Cinema cinema;
    private boolean closed = false;

    public CinemaResourceManager(Cinema cinema) {
        this.cinema = cinema;
        System.out.println("CinemaResourceManager initialized for: " + cinema.getName());
    }

    public void addCustomer(Customer customer) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Customer> existingCustomers = cinema.getCustomers();
        if (existingCustomers == null) {
            existingCustomers = new ArrayList<>();
            cinema.setCustomers(existingCustomers);
        }
        existingCustomers.add(customer);
        System.out.println("Added customer: " + customer.getName());
    }

    public void addStaff(Staff staff) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Staff> existingStaff = cinema.getStaffMembers();
        if (existingStaff == null) {
            existingStaff = new ArrayList<>();
            cinema.setStaffMembers(existingStaff);
        }
        existingStaff.add(staff);
        System.out.println("Added staff member: " + staff.getName());
    }

    public void addProduct(Product product) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Product> existingProducts = cinema.getProducts();
        if (existingProducts == null) {
            existingProducts = new ArrayList<>();
            cinema.setProducts(existingProducts);
        }
        existingProducts.add(product);
        System.out.println("Added product: " + product.getName());
    }


    public String getCinemaInfo() {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        return "Cinema: " + cinema.getName() + " (" + cinema.getLocation() + ")";
    }


    public int getCustomerCount() {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Customer> customers = cinema.getCustomers();
        return customers != null ? customers.size() : 0;
    }

    public int getStaffCount() {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Staff> staff = cinema.getStaffMembers();
        return staff != null ? staff.size() : 0;
    }


    public int getProductCount() {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Product> products = cinema.getProducts();
        return products != null ? products.size() : 0;
    }

    public boolean isClosed() {
        return closed;
    }


    // Predicate - Lambda Function
    public List<Customer> getCustomersBy(Predicate<Customer> filter) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Customer> customers = cinema.getCustomers();
        if (customers == null || customers.isEmpty()) {
            return new ArrayList<>();
        }

        List<Customer> filteredCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (filter.test(customer)) {
                filteredCustomers.add(customer);
            }
        }

        return filteredCustomers;
    }

    public List<Customer> getLateCustomers(LocalDate date) {
        return getCustomersBy(customer -> 
            customer.getRegistrationDate() != null && 
            customer.getRegistrationDate().isAfter(date)
        );
    }

    public List<Customer> getCustomersByMailDomain(String domain) {
        return getCustomersBy(customer -> 
            customer.getEmail() != null && 
            customer.getEmail().endsWith("@" + domain)
        );
    }


    //Supplier - Lambda Function
    public Product buildProduct(Supplier<Product> productFactory) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }
        return productFactory.get();
    }

    public Product createPopularSnack() {
        return buildProduct(() -> {
            List<String> popularSnacks = Arrays.asList("Popcorn", "Candy", "Chips", "Soda", "Water");
            return new Snack(popularSnacks.get(0));
        });
    }

    // Custom Lambda Expression #2 - Cinema Operations
    public void performCinemaOperation(Consumer<Cinema> operation) {
        if (closed) {
            throw new IllegalStateException("Resource manager is closed");
        }
        operation.accept(cinema);
    }

    public void displayCinemaStatus() {
        performCinemaOperation(cinema -> {
            System.out.println("=== Cinema Status ===");
            System.out.println("Name: " + cinema.getName());
            System.out.println("Location: " + cinema.getLocation());
            System.out.println("Total Capacity: " + cinema.getTotalCapacity());
            System.out.println("====================");
        });
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            System.out.println("Closing CinemaResourceManager for: " + cinema.getName());
            System.out.println("Final counts - Customers: " + getCustomerCount() +
                    ", Staff: " + getStaffCount() +
                    ", Products: " + getProductCount());
            closed = true;
        }
    }
}


