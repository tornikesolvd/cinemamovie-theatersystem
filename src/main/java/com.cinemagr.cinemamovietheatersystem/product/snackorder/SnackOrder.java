package com.cinemagr.cinemamovietheatersystem.product.snackorder;

import com.cinemagr.cinemamovietheatersystem.annotation.Validate;
import com.cinemagr.cinemamovietheatersystem.product.Snack;
import com.cinemagr.cinemamovietheatersystem.showpiece.Showpiece;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class SnackOrder extends Showpiece {

    @Validate(required = true)
    private List<Snack> snacks;


    private static final Logger LOGGER = LogManager.getLogger(SnackOrder.class);
    {
        LOGGER.debug("SnackOrder instance created (instance initializer)");
    }

    public SnackOrder(List<Snack> snacks) {
        this.snacks = snacks;
    }

    public List<Snack> getSnacks() {
        return snacks;
    }

    public void setSnacks(List<Snack> snacks) {
        this.snacks = snacks;
    }

    public int getSnackCount() {
        return snacks == null ? 0 : snacks.size();
    }

    @Validate("total_calculation")
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        try {
            if (snacks != null) {
                total = snacks.stream()
                        .filter(snack -> snack != null && snack.getPrice() != null)
                        .map(Snack::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            return total;
        } finally {
            LOGGER.info("SnackOrder total calculated: ${}", total);
        }
    }


    @Override
    public boolean isValid() {
        return snacks != null;
    }

    @Override
    public String getDisplayname() {
        return "Snacks: " + getSnacks().toString();
    }
}
