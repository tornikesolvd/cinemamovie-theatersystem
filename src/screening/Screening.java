package screening;

import movie.Movie;
import theaterhall.TheaterHall;
import ticket.Ticket;

import java.time.LocalDateTime;

public class Screening {

    private TheaterHall theaterHall;
    private Movie movie;
    private LocalDateTime time;
    private Ticket[] tickets;

    //during screening, there must be mentioned movie and time on when it's screening,but it could have left 0 tickets.+
    public Screening(TheaterHall theaterHall) {
        this.theaterHall = theaterHall;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Ticket[] getTickets() {
        return tickets;
    }

    public void setTickets(Ticket[] tickets) {
        this.tickets = tickets;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
