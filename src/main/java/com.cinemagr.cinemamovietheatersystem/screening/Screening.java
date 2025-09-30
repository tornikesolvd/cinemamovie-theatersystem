package com.cinemagr.cinemamovietheatersystem.screening;

import com.cinemagr.cinemamovietheatersystem.contract.Schedulable;
import com.cinemagr.cinemamovietheatersystem.movie.Movie;
import com.cinemagr.cinemamovietheatersystem.showpiece.Showpiece;
import com.cinemagr.cinemamovietheatersystem.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public class Screening extends Showpiece implements Schedulable {

    private Movie movie;
    private LocalDateTime time;
    private List<Ticket> tickets;

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public String getDisplayname() {
        return "Movie Screening: " + "Movie: " + movie.getTitle() + "Time: " + time;
    }
}
