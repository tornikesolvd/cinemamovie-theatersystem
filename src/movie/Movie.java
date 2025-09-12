package movie;


import showpiece.Showpiece;

import java.util.Objects;

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
    public int hashCode() {
        return Objects.hash(title, duration);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Movie movie)) return false;
        return Objects.equals(title, movie.title) && duration == movie.duration;
    }
}
