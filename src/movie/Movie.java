package movie;


import showpiece.Showpiece;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Movie extends Showpiece {

    private String title;
    private Integer duration;
    private String genre;

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
}
