package com.cinemagr.cinemamovietheatersystem.cinema;

public enum CinemaStatus {
    OPEN("Open", true, "Cinema is operating normally"),
    CLOSED("Closed", false, "Cinema is temporarily closed"),
    MAINTENANCE("Maintenance", false, "Cinema is under maintenance"),
    FULL_CAPACITY("Full Capacity", true, "Cinema is at maximum capacity"),
    EMERGENCY("Emergency", false, "Cinema is in emergency mode"),
    RENOVATION("Renovation", false, "Cinema is being renovated"),
    SPECIAL_EVENT("Special Event", true, "Cinema is hosting special event");

    private final String statusName;
    private final boolean operational;
    private final String description;
    private static int totalStatuses = 0;

    static {
        totalStatuses = values().length;
        System.out.println("CinemaStatus enum initialized with " + totalStatuses + " statuses");
    }

    CinemaStatus(String statusName, boolean operational, String description) {
        this.statusName = statusName;
        this.operational = operational;
        this.description = description;
    }

    public String getStatusName() {
        return statusName;
    }

    public boolean operational() {
        return operational;
    }

    public String getDescription() {
        return description;
    }

    public boolean canSellTickets() {
        return operational && this != FULL_CAPACITY;
    }

    public boolean requiresStaff() {
        return operational || this == MAINTENANCE;
    }

    public String getStatusMessage() {
        return "Cinema Status: " + statusName + " - " + description;
    }

    public static int getTotalStatuses() {
        return totalStatuses;
    }

    public static CinemaStatus getOperationalStatus() {
        return java.util.Arrays.stream(values())
                .filter(CinemaStatus::operational)
                .findFirst()
                .orElse(CLOSED);
    }

    public static CinemaStatus getNonOperationalStatus() {
        return java.util.Arrays.stream(values())
                .filter(status -> !status.operational())
                .findFirst()
                .orElse(OPEN);
    }

    public CinemaStatus getNextStatus() {
        CinemaStatus[] statuses = values();
        int currentIndex = this.ordinal();
        int nextIndex = (currentIndex + 1) % statuses.length;
        return statuses[nextIndex];
    }
}
