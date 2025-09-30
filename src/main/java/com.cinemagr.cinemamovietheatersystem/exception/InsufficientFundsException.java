package com.cinemagr.cinemamovietheatersystem.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    private final BigDecimal requiredAmount;
    private final BigDecimal availableAmount;

    public InsufficientFundsException(BigDecimal requiredAmount, BigDecimal availableAmount) {
        super("Insufficient funds. Required: $" + requiredAmount + ", Available: $" + availableAmount);
        this.requiredAmount = requiredAmount;
        this.availableAmount = availableAmount;
    }

    public InsufficientFundsException(String message, BigDecimal requiredAmount, BigDecimal availableAmount) {
        super(message);
        this.requiredAmount = requiredAmount;
        this.availableAmount = availableAmount;
    }

    public BigDecimal getRequiredAmount() {
        return requiredAmount;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }
}


