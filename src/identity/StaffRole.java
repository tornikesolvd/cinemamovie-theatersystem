package identity;

import java.math.BigDecimal;

public enum StaffRole {
    MANAGER("Manager", new BigDecimal("25.00"), 8, "Oversees cinema operations"),
    CASHIER("Cashier", new BigDecimal("15.00"), 6, "Handles ticket sales"),
    USHER("Usher", new BigDecimal("12.00"), 4, "Assists customers and maintains order"),
    CLEANER("Cleaner", new BigDecimal("10.00"), 5, "Maintains cleanliness"),
    TECHNICIAN("Technician", new BigDecimal("20.00"), 7, "Maintains equipment"),
    SECURITY("Security", new BigDecimal("18.00"), 8, "Ensures safety"),
    CONCESSION_WORKER("Concession Worker", new BigDecimal("13.00"), 4, "Sells snacks and drinks"),
    PROJECTIONIST("Projectionist", new BigDecimal("16.00"), 6, "Manages movie projection");

    private final String roleName;
    private final BigDecimal hourlyWage;
    private final int maxHoursPerDay;
    private final String responsibilities;
    private static int totalRoles = 0;

    static {
        totalRoles = values().length;
        System.out.println("StaffRole enum initialized with " + totalRoles + " roles");
    }

    StaffRole(String roleName, BigDecimal hourlyWage, int maxHoursPerDay, String responsibilities) {
        this.roleName = roleName;
        this.hourlyWage = hourlyWage;
        this.maxHoursPerDay = maxHoursPerDay;
        this.responsibilities = responsibilities;
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

    public boolean isManagement() {
        return this == MANAGER;
    }

    public boolean isCustomerFacing() {
        return this == CASHIER || this == USHER || this == CONCESSION_WORKER;
    }

    public boolean isTechnical() {
        return this == TECHNICIAN || this == PROJECTIONIST;
    }

    public String getRoleInfo() {
        return roleName + " - $" + hourlyWage + "/hour (" + maxHoursPerDay + "h max)";
    }

    public static int getTotalRoles() {
        return totalRoles;
    }

    public static StaffRole getHighestPaidRole() {
        StaffRole highest = MANAGER;
        for (StaffRole role : values()) {
            if (role.hourlyWage.compareTo(highest.hourlyWage) > 0) {
                highest = role;
            }
        }
        return highest;
    }

    public static StaffRole getLowestPaidRole() {
        StaffRole lowest = MANAGER;
        for (StaffRole role : values()) {
            if (role.hourlyWage.compareTo(lowest.hourlyWage) < 0) {
                lowest = role;
            }
        }
        return lowest;
    }

    public boolean requiresSpecialTraining() {
        return this == TECHNICIAN || this == PROJECTIONIST || this == SECURITY;
    }
}
