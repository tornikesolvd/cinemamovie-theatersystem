package com.cinemagr.cinemamovietheatersystem.identity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;

public enum StaffRole {

    MANAGER("Manager", new BigDecimal("25.00"), 8, "Oversees cinema operations"),
    CASHIER("Cashier", new BigDecimal("15.00"), 6, "Handles ticket sales"),
    USHER("Usher", new BigDecimal("12.00"), 4, "Assists customers and maintains order"),
    CLEANER("Cleaner", new BigDecimal("10.00"), 5, "Maintains cleanliness"),
    TECHNICIAN("Technician", new BigDecimal("20.00"), 7, "Maintains equipment"),
    SECURITY("Security", new BigDecimal("18.00"), 8, "Ensures safety"),
    CONCESSION_WORKER("Concession Worker", new BigDecimal("13.00"), 4, "Sells snacks and drinks"),
    PROJECTIONIST("Projectionist", new BigDecimal("16.00"), 6, "Manages movie projection");

    private static final Logger LOGGER = LogManager.getLogger(StaffRole.class);
    private static int totalRoles = 0;

    static {
        totalRoles = values().length;
        LOGGER.info("StaffRole enum initialized with {} roles", totalRoles);
    }

    private final String roleName;
    private final BigDecimal hourlyWage;
    private final int maxHoursPerDay;
    private final String responsibilities;

    StaffRole(String roleName, BigDecimal hourlyWage, int maxHoursPerDay, String responsibilities) {
        this.roleName = roleName;
        this.hourlyWage = hourlyWage;
        this.maxHoursPerDay = maxHoursPerDay;
        this.responsibilities = responsibilities;
    }

    public static int getTotalRoles() {
        return totalRoles;
    }

    public static StaffRole getHighestPaidRole() {
        return Arrays.stream(values())
                .max((r1, r2) -> r1.hourlyWage.compareTo(r2.hourlyWage))
                .orElse(MANAGER);
    }

    public static StaffRole getLowestPaidRole() {
        return Arrays.stream(values())
                .min((r1, r2) -> r1.hourlyWage.compareTo(r2.hourlyWage))
                .orElse(MANAGER);
    }

    public String getRoleName() {
        return roleName;
    }

    public BigDecimal getHourlyWage() {
        return hourlyWage;
    }

    public int getMaxHoursPerDay() {
        return maxHoursPerDay;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public BigDecimal calculateDailyWage() {
        return hourlyWage.multiply(BigDecimal.valueOf(maxHoursPerDay));
    }

    public BigDecimal calculateWeeklyWage() {
        return calculateDailyWage().multiply(BigDecimal.valueOf(5));
    }

    public boolean management() {
        return this == MANAGER;
    }

    public boolean customerFacing() {
        return this == CASHIER || this == USHER || this == CONCESSION_WORKER;
    }

    public boolean technical() {
        return this == TECHNICIAN || this == PROJECTIONIST;
    }

    public String getRoleInfo() {
        return roleName + " - $" + hourlyWage + "/hour (" + maxHoursPerDay + "h max)";
    }

    public boolean requiresSpecialTraining() {
        return this == TECHNICIAN || this == PROJECTIONIST || this == SECURITY;
    }
}
