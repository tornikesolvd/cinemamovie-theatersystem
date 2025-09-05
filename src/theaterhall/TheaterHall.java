package theaterhall;

import screening.Screening;

public class TheaterHall {

    private int hallNumber;
    private int capacity;
    private Screening[] screenings;

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

    public Screening[] getScreenings() {
        return screenings;
    }

    public void setScreenings(Screening[] screenings) {
        this.screenings = screenings;
    }
}
