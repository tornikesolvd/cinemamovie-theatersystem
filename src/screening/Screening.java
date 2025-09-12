package screening;

import showpiece.Showpiece;
import movie.Movie;
import ticket.Ticket;

import java.time.LocalDateTime;

public class Screening extends Showpiece {

    private Movie movie;
    private LocalDateTime time;
    private Ticket[] tickets;

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
