package resource;

import cinema.Cinema;
import identity.Customer;
import identity.Staff;
import product.Product;
import theaterhall.TheaterHall;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CinemaResourceManager implements Closeable {

    private final Cinema cinema;
    private boolean isClosed = false;

    public CinemaResourceManager(Cinema cinema) {
        this.cinema = cinema;
        System.out.println("CinemaResourceManager initialized for: " + cinema.getName());
    }

    public void addCustomer(Customer customer) {
        if (isClosed) {
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
        if (isClosed) {
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
        if (isClosed) {
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
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        return "Cinema: " + cinema.getName() + " (" + cinema.getLocation() + ")";
    }


    public int getCustomerCount() {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Customer> customers = cinema.getCustomers();
        return customers != null ? customers.size() : 0;
    }

    public int getStaffCount() {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Staff> staff = cinema.getStaffMembers();
        return staff != null ? staff.size() : 0;
    }


    public int getProductCount() {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        List<Product> products = cinema.getProducts();
        return products != null ? products.size() : 0;
    }

    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void close() throws IOException {
        if (!isClosed) {
            System.out.println("Closing CinemaResourceManager for: " + cinema.getName());
            System.out.println("Final counts - Customers: " + getCustomerCount() +
                    ", Staff: " + getStaffCount() +
                    ", Products: " + getProductCount());
            isClosed = true;
        }
    }
}


