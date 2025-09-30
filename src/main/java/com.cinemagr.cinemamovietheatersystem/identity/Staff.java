package com.cinemagr.cinemamovietheatersystem.identity;

import com.cinemagr.cinemamovietheatersystem.annotation.DisplayInfo;
import com.cinemagr.cinemamovietheatersystem.annotation.Validate;

import java.math.BigDecimal;

@DisplayInfo(value = "Staff Member", description = "Cinema staff member with specific role")
public class Staff extends Person {

    @Validate(required = true)
    private StaffRole role;

    public Staff(String name, StaffRole role) {
        super(name);
        this.role = role;
    }

    public Staff(String name, String roleName) {
        super(name);
        try {
            this.role = StaffRole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.role = StaffRole.USHER;
        }
    }

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public void setRole(String roleName) {
        try {
            this.role = StaffRole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.role = StaffRole.USHER;
        }
    }

    @Override
    public String getRoleDescription() {
        return role != null ? role.getResponsibilities() : "No role assigned";
    }

    @DisplayInfo(value = "Hourly Wage", description = "Calculates the hourly wage for this staff member")
    @Validate("wage_calculation")
    public BigDecimal getHourlyWage() {
        return role != null ? role.getHourlyWage() : BigDecimal.ZERO;
    }

    public BigDecimal calculateDailyWage() {
        return role != null ? role.calculateDailyWage() : BigDecimal.ZERO;
    }

    public BigDecimal calculateWeeklyWage() {
        return role != null ? role.calculateWeeklyWage() : BigDecimal.ZERO;
    }

    public boolean management() {
        return role != null && role.management();
    }

    public int getMaxHoursPerDay() {
        return role != null ? role.getMaxHoursPerDay() : 0;
    }

    @Override
    public boolean isValid() {
        return name != null && role != null;
    }

    @Override
    public String getDisplayname() {
        return "Staff " + name + " - " + (role != null ? role.getRoleName() : "No Role");
    }
}
