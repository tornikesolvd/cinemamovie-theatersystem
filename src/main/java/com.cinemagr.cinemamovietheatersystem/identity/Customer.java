package com.cinemagr.cinemamovietheatersystem.identity;

import com.cinemagr.cinemamovietheatersystem.product.snackorder.SnackOrder;

import java.time.LocalDate;
import java.util.List;

public class Customer extends Person {

    private LocalDate registrationDate;
    private List<SnackOrder> orders;
    private String name = "solvd";

    //as customer is person and extends Person class, therefore customer should have name as obligatory field, but not registration date or orders because customer can not be registered and does not have any orders but stil attend cinema.
    public Customer(String name) {
        super(name);
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<SnackOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<SnackOrder> orders) {
        this.orders = orders;
    }

    @Override
    public String getRoleDescription() {
        return "Cinema customer";
    }

    @Override
    public boolean isValid() {
        return registrationDate != null && name != null;
    }

    @Override
    public String getDisplayname() {
        return "Customer: " + name + "Registration Date: " + registrationDate;
    }
}

