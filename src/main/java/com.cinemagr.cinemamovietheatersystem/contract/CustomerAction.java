package com.cinemagr.cinemamovietheatersystem.contract;


import com.cinemagr.cinemamovietheatersystem.identity.Customer;

@FunctionalInterface
public interface CustomerAction {
    void execute(Customer customer);
}
