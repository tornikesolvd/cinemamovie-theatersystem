package resource;

import cinema.Cinema;
import identity.Customer;
import identity.Staff;
import product.Product;
import theaterhall.TheaterHall;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;

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

        Customer[] existingCustomers = cinema.getCustomers();
        if (existingCustomers == null) {
            cinema.setCustomers(new Customer[]{customer});
        } else {
            Customer[] newCustomers = Arrays.copyOf(existingCustomers, existingCustomers.length + 1);
            newCustomers[existingCustomers.length] = customer;
            cinema.setCustomers(newCustomers);
        }
        System.out.println("Added customer: " + customer.getName());
    }

    public void addStaff(Staff staff) {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        Staff[] existingStaff = cinema.getStaffMembers();
        if (existingStaff == null) {
            cinema.setStaffMembers(new Staff[]{staff});
        } else {
            Staff[] newStaff = Arrays.copyOf(existingStaff, existingStaff.length + 1);
            newStaff[existingStaff.length] = staff;
            cinema.setStaffMembers(newStaff);
        }
        System.out.println("Added staff member: " + staff.getName());
    }

    public void addProduct(Product product) {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        Product[] existingProducts = cinema.getProducts();
        if (existingProducts == null) {
            cinema.setProducts(new Product[]{product});
        } else {
            Product[] newProducts = Arrays.copyOf(existingProducts, existingProducts.length + 1);
            newProducts[existingProducts.length] = product;
            cinema.setProducts(newProducts);
        }
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

        Customer[] customers = cinema.getCustomers();
        return customers != null ? customers.length : 0;
    }

    public int getStaffCount() {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        Staff[] staff = cinema.getStaffMembers();
        return staff != null ? staff.length : 0;
    }


    public int getProductCount() {
        if (isClosed) {
            throw new IllegalStateException("Resource manager is closed");
        }

        Product[] products = cinema.getProducts();
        return products != null ? products.length : 0;
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

