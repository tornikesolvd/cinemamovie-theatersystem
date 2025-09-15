package cinema;

import identity.Person;
import product.Product;
import showpiece.Showpiece;
import identity.Customer;
import identity.Staff;
import theaterhall.TheaterHall;
import exception.CinemaCapacityExceededException;

public class Cinema extends Showpiece {

    private String name;
    private String location;
    private TheaterHall[] halls;
    private Staff[] staffMembers;
    private Customer[] customers;
    private Product[] products; //super type field

    // cinema should have name, location and halls , but not staffmembers or customers.
    public Cinema(String name, String location, TheaterHall[] halls) {
        this.name = name;
        this.location = location;
        this.halls = halls;
    }

    @Override
    public boolean isValid() {
        return name != null && !name.isBlank()
                && location != null && !location.isBlank()
                && halls != null && halls.length > 0;
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

    public TheaterHall[] getHalls() {
        return halls;
    }

    public Staff[] getStaffMembers() {
        return staffMembers;
    }

    public void setStaffMembers(Staff[] staffMembers) {
        this.staffMembers = staffMembers;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }


    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }

        Customer[] existingCustomers = this.customers;
        int currentCount = existingCustomers != null ? existingCustomers.length : 0;
        int maxCapacity = 100; // Maximum cinema capacity

        if (currentCount >= maxCapacity) {
            throw new CinemaCapacityExceededException(currentCount, maxCapacity);
        }

        if (existingCustomers == null) {
            this.customers = new Customer[]{customer};
        } else {
            Customer[] newCustomers = new Customer[existingCustomers.length + 1];
            System.arraycopy(existingCustomers, 0, newCustomers, 0, existingCustomers.length);
            newCustomers[existingCustomers.length] = customer;
            this.customers = newCustomers;
        }
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
