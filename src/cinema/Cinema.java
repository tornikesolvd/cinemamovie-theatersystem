package cinema;

import product.Product;
import showpiece.Showpiece;
import identity.Customer;
import identity.Staff;
import theaterhall.TheaterHall;
import exception.CinemaCapacityExceededException;

import java.util.List;
import java.util.ArrayList;
import contract.CustomerAction;

public class Cinema extends Showpiece {

    private String name;
    private String location;
    private List<TheaterHall> halls;
    private List<Staff> staffMembers;
    private List<Customer> customers;
    private List<Product> products;
    private CinemaStatus status;

    // cinema should have name, location and halls , but not staffmembers or customers.
    public Cinema(String name, String location, List<TheaterHall> halls) {
        this.name = name;
        this.location = location;
        this.halls = halls;
        this.status = CinemaStatus.OPEN;
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

    public CinemaStatus getStatus() {
        return status;
    }

    public void setStatus(CinemaStatus status) {
        this.status = status;
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
        return halls.stream()
                .mapToInt(TheaterHall::getCapacity)
                .sum();
    }

    public void technicalTask(Runnable maintenanceTask) {
        System.out.println("Executing maintenance task at " + name);
        maintenanceTask.run();
    }


    public void cleanTheaterHall(TheaterHall hall, Runnable cleaningTask) {
        System.out.println("Starting cleaning of hall " + hall.getId() + " at " + name);
        cleaningTask.run();
        System.out.println("Cleaning completed for hall " + hall.getId() + " at " + name);
    }

    public void applyCustomerAction(CustomerAction customerAction) {
        if (customers != null) {
            System.out.println("Applying action to " + customers.size() + " customers at " + name);
            customers.stream()
                    .forEach(customerAction::execute);
        }
    }

    public boolean canSellTickets() {
        return status != null && status.canSellTickets();
    }

    public boolean requiresStaff() {
        return status != null && status.requiresStaff();
    }

    public String getStatusMessage() {
        return status != null ? status.getStatusMessage() : "Status not set";
    }

    public void changeStatus(CinemaStatus newStatus) {
        System.out.println("Changing cinema status from " + this.status + " to " + newStatus);
        this.status = newStatus;
    }

}
