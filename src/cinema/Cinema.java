package cinema;

import identity.Person;
import product.Product;
import showpiece.Showpiece;
import identity.Customer;
import identity.Staff;
import theaterhall.TheaterHall;
import exception.CinemaCapacityExceededException;
import java.util.List;
import java.util.ArrayList;

public class Cinema extends Showpiece {

    private String name;
    private String location;
    private List<TheaterHall> halls;
    private List<Staff> staffMembers;
    private List<Customer> customers;
    private List<Product> products;

    // cinema should have name, location and halls , but not staffmembers or customers.
    public Cinema(String name, String location, List<TheaterHall> halls) {
        this.name = name;
        this.location = location;
        this.halls = halls;
    }

    @Override
    public boolean isValid() {
        return name != null && !name.isBlank()
                && location != null && !location.isBlank()
                && halls != null && !halls.isEmpty();
    }

    @Override
    public String getDisplayname() {
        return "Cinema: " + name + " in " + location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<TheaterHall> getHalls() {
        return halls;
    }

    public List<Staff> getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(List<Staff> staffMembers) {
        this.staffMembers = staffMembers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        List<Customer> existingCustomers = this.customers;
        int currentCount = existingCustomers != null ? existingCustomers.size() : 0;
        int maxCapacity = 100; // Maximum cinema capacity

        if (currentCount >= maxCapacity) {
            throw new CinemaCapacityExceededException(currentCount, maxCapacity);
        }

        if (existingCustomers == null) {
            this.customers = new ArrayList<>();
        }
        this.customers.add(customer);
    }

    public int getTotalCapacity() {
        if (halls == null) return 0;
        int totalCapacity = 0;
        for (TheaterHall hall : halls) {
            totalCapacity += hall.getCapacity();
        }
        return totalCapacity;
    }

}
