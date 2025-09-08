package movie;

public class Movie {

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
}
