package com.cinemagr.cinemamovietheatersystem.movie;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovieInfo(
        String title,
        String director,
        LocalDate releaseDate,
        Integer duration,
        BigDecimal budget,
        String studio
) {
    public MovieInfo {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        if (director == null || director.isBlank()) {
            throw new IllegalArgumentException("Director cannot be null or blank");
        }
        if (releaseDate == null) {
            throw new IllegalArgumentException("Release date cannot be null");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        if (budget == null || budget.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Budget cannot be null or negative");
        }
        if (studio == null || studio.isBlank()) {
            throw new IllegalArgumentException("Studio cannot be null or blank");
        }
    }

    public boolean recent() {
        return releaseDate.isAfter(LocalDate.now().minusYears(2));
    }

    public boolean classic() {
        return releaseDate.isBefore(LocalDate.now().minusYears(20));
    }

    public String getFormattedDuration() {
        int hours = duration / 60;
        int minutes = duration % 60;
        if (hours > 0) {
            return hours + "h " + minutes + "min";
        } else {
            return minutes + "min";
        }
    }

    public String getBudgetFormatted() {
        if (budget.compareTo(new BigDecimal("1000000")) >= 0) {
            return "$" + budget.divide(new BigDecimal("1000000")) + "M";
        } else if (budget.compareTo(new BigDecimal("1000")) >= 0) {
            return "$" + budget.divide(new BigDecimal("1000")) + "K";
        } else {
            return "$" + budget;
        }
    }

    public String getFullInfo() {
        return title + " by " + director + " (" + releaseDate.getYear() + ") - " + getFormattedDuration() + " - " + getBudgetFormatted();
    }

    public boolean highBudget() {
        return budget.compareTo(new BigDecimal("100000000")) >= 0;
    }

    public boolean lowBudget() {
        return budget.compareTo(new BigDecimal("1000000")) < 0;
    }

    public int getAgeInYears() {
        return LocalDate.now().getYear() - releaseDate.getYear();
    }

    public String getCategory() {
        if (highBudget()) return "Blockbuster";
        if (lowBudget()) return "Indie";
        return "Standard";
    }
}
