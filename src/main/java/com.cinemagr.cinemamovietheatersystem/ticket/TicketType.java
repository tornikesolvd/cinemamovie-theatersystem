package com.cinemagr.cinemamovietheatersystem.ticket;

import java.math.BigDecimal;
import java.util.Arrays;

public enum TicketType {
    STANDARD("Standard", new BigDecimal("12.50"), 1.0),
    VIP("VIP", new BigDecimal("25.00"), 1.5),
    STUDENT("Student", new BigDecimal("8.00"), 0.8),
    SENIOR("Senior", new BigDecimal("6.00"), 0.6),
    CHILD("Child", new BigDecimal("5.00"), 0.5),
    PREMIUM("Premium", new BigDecimal("35.00"), 2.0),
    MATINEE("Matinee", new BigDecimal("9.00"), 0.9);

    private final String typeName;
    private final BigDecimal basePrice;
    private final double multiplier;
    private static int totalTypes = 0;

    static {
        totalTypes = values().length;
        System.out.println("TicketType enum initialized with " + totalTypes + " types");
    }

    TicketType(String typeName, BigDecimal basePrice, double multiplier) {
        this.typeName = typeName;
        this.basePrice = basePrice;
        this.multiplier = multiplier;
    }

    public String getTypeName() {
        return typeName;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
        return basePrice.multiply(BigDecimal.valueOf(multiplier));
    }

    public boolean discounted() {
        return multiplier < 1.0;
    }

    public boolean premium() {
        return multiplier > 1.5;
    }

    public String getPriceInfo() {
        return typeName + ": $" + basePrice + " (x" + multiplier + ")";
    }

    public static int getTotalTypes() {
        return totalTypes;
    }


    public static TicketType getCheapestType() {
        return Arrays.stream(values())
                .min((t1, t2) -> t1.basePrice.compareTo(t2.basePrice))
                .orElse(STANDARD);
    }

    public static TicketType getMostExpensiveType() {
        return Arrays.stream(values())
                .max((t1, t2) -> t1.basePrice.compareTo(t2.basePrice))
                .orElse(STANDARD);
    }
}
