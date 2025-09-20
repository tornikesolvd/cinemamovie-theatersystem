package ticket;

import java.math.BigDecimal;

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

    public boolean isDiscounted() {
        return multiplier < 1.0;
    }

    public boolean isPremium() {
        return multiplier > 1.5;
    }

    public String getPriceInfo() {
        return typeName + ": $" + basePrice + " (x" + multiplier + ")";
    }

    public static int getTotalTypes() {
        return totalTypes;
    }


    public static TicketType getCheapestType() {
        TicketType cheapest = STANDARD;
        for (TicketType type : values()) {
            if (type.basePrice.compareTo(cheapest.basePrice) < 0) {
                cheapest = type;
            }
        }
        return cheapest;
    }

    public static TicketType getMostExpensiveType() {
        TicketType expensive = STANDARD;
        for (TicketType type : values()) {
            if (type.basePrice.compareTo(expensive.basePrice) > 0) {
                expensive = type;
            }
        }
        return expensive;
    }
}
