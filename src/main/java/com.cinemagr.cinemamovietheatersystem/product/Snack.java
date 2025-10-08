package com.cinemagr.cinemamovietheatersystem.product;

import com.cinemagr.cinemamovietheatersystem.contract.Consumable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class Snack extends Product implements Consumable {

    private static final Logger LOGGER = LogManager.getLogger(Snack.class);

    public Snack(String name) {
        super(name);
    }

    @Override
    public BigDecimal getFinalPrice()  {
        return price;
    }

    @Override
    public void consume() {
        LOGGER.info("Enjoy your {}!", name);
    }
}
