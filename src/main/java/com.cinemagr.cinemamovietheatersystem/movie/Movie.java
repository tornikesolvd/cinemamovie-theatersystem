package com.cinemagr.cinemamovietheatersystem.movie;


import com.cinemagr.cinemamovietheatersystem.showpiece.Showpiece;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Movie extends Showpiece {

    private String title;
    private Integer duration;
    private MovieGenre genre;
    private MovieInfo movieInfo;

    //Each movie has title and duration of course that's obligatory - the reason why I have mentioned those fields in constructor.
    public Movie(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public MovieGenre getGenre() {
        return genre;
    }

    public void setGenre(MovieGenre genre) {
        this.genre = genre;
    }

    public void setGenre(String genreName) {
        try {
            this.genre = MovieGenre.valueOf(genreName.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.genre = MovieGenre.DRAMA;
        }
    }

    public MovieInfo getMovieInfo() {
        return movieInfo;
    }

    public void setMovieInfo(MovieInfo movieInfo) {
        this.movieInfo = movieInfo;
    }

    @Override
    public String toString() {
        return "Movie{" + title + ", " + duration + "min, " + genre + "}";
    }

    @Override
    public boolean isValid() {
        return title != null && duration != null && duration > 0;
    }

    @Override
    public String getDisplayname() {
        return "Movie " + title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Movie movie)) return false;
        return Objects.equals(title, movie.title) && duration == movie.duration;
    }

    public String transformMovie(Function<Movie, String> movieTransform) {
        return movieTransform.apply(this);
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

    public String getShortInfo() {
        return transformMovie(movie ->
                movie.getTitle() + " (" + movie.getFormattedDuration() + ")"
        );
    }

    public String checkMovie(Predicate<Movie> ratingCriteria) {
        if (ratingCriteria.test(this)) {
            return "This movie meets the criteria!";
        } else {
            return "This movie doesn't meet the criteria.";
        }
    }

    public boolean suitableForAge(int age) {
        return genre != null && genre.suitableForAge(age);
    }

    public String getAgeRating() {
        return genre != null ? genre.getAgeRating() : "UNKNOWN";
    }

    public String getGenreDescription() {
        return genre != null ? genre.getDescription() : "No genre set";
    }

    public String getFullMovieInfo() {
        if (movieInfo != null) {
            return movieInfo.getFullInfo();
        }
        return getShortInfo() + " - " + getGenreDescription();
    }
}
