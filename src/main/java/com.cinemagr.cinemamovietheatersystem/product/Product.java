package com.cinemagr.cinemamovietheatersystem.product;

import com.cinemagr.cinemamovietheatersystem.contract.Identifiable;
import com.cinemagr.cinemamovietheatersystem.contract.Payable;

import java.math.BigDecimal;

public abstract class Product implements Payable, Identifiable {

    protected String name;
    protected BigDecimal price;

    public Product(String name) {
        this.name = name;
        this.price = BigDecimal.ZERO;
    }


    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public abstract BigDecimal getFinalPrice();
}
