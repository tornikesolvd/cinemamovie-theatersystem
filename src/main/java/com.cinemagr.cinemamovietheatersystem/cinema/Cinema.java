package com.cinemagr.cinemamovietheatersystem.cinema;

import com.cinemagr.cinemamovietheatersystem.MainClass;
import com.cinemagr.cinemamovietheatersystem.contract.CustomerAction;
import com.cinemagr.cinemamovietheatersystem.exception.CinemaCapacityExceededException;
import com.cinemagr.cinemamovietheatersystem.identity.Customer;
import com.cinemagr.cinemamovietheatersystem.identity.Staff;
import com.cinemagr.cinemamovietheatersystem.product.Product;
import com.cinemagr.cinemamovietheatersystem.showpiece.Showpiece;
import com.cinemagr.cinemamovietheatersystem.theaterhall.TheaterHall;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class Cinema extends Showpiece {

    private static final Logger LOGGER = LogManager.getLogger(Cinema.class);
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
        LOGGER.info("Executing maintenance task at {}", name);
        maintenanceTask.run();
    }


    public void cleanTheaterHall(TheaterHall hall, Runnable cleaningTask) {
        LOGGER.info("Starting cleaning of hall {} at {}", hall.getId(), name);
        cleaningTask.run();
        LOGGER.info("Cleaning completed for hall {} at {}", hall.getId(), name);
    }

    public void applyCustomerAction(CustomerAction customerAction) {
        if (customers != null) {
            LOGGER.info("Applying action to {} customers at {}", customers.size(), name);
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
        LOGGER.info("Changing cinema status from {} to {}", this.status, newStatus);
        this.status = newStatus;
    }

}
