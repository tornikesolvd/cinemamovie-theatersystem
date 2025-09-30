package com.cinemagr.cinemamovietheatersystem.theaterhall;


import com.cinemagr.cinemamovietheatersystem.screening.Screening;
import com.cinemagr.cinemamovietheatersystem.showpiece.Showpiece;

import java.util.LinkedHashSet;
import java.util.Set;

public class TheaterHall extends Showpiece {

    private Integer hallNumber;
    private int capacity;
    private Set<Screening> screenings;

    //in theater hall there could be 0 screenings, but it has number of the hall and capacity - which can be 0 also but its mandatory to mention in the constructor.
    public TheaterHall(int hallNumber, int capacity) {
        this.hallNumber = hallNumber;
        this.capacity = capacity;
    }

    public int getHallNumber() {
        return hallNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(Set<Screening> screenings) {
        this.screenings = screenings;
    }

    public void addScreening(Screening screening) {
        if (this.screenings == null) {
            this.screenings = new LinkedHashSet<>();
        }
        this.screenings.add(screening);
    }

    @Override
    public boolean isValid() {
        return hallNumber != null && hallNumber < capacity && hallNumber > 0
                && capacity > 0;
    }

    @Override
    public String getDisplayname() {
        return "d";
    }
}
