package com.cinemagr.cinemamovietheatersystem.product;

import com.cinemagr.cinemamovietheatersystem.contract.Consumable;

import java.math.BigDecimal;

public class Snack extends Product implements Consumable {

    public Snack(String name) {
        super(name);
    }

    @Override
    public BigDecimal getFinalPrice() {
        return price;
    }

    @Override
    public void consume() {
        System.out.println("Enjoy your " + name + "!");
    }
}
