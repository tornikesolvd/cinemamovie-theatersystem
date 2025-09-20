package movie;

public enum MovieGenre {
    ACTION("Action", 15, "High energy films"),
    COMEDY("Comedy", 12, "Funny entertainment"),
    DRAMA("Drama", 18, "Serious storytelling"),
    HORROR("Horror", 16, "Scary movies"),
    SCI_FI("Science Fiction", 13, "Futuristic stories"),
    ROMANCE("Romance", 14, "Love stories"),
    THRILLER("Thriller", 16, "Suspenseful plots"),
    ANIMATION("Animation", 6, "Animated features");

    private final String displayName;
    private final int minimumAge;
    private final String description;
    private static int totalGenres = 0;

    static {
        totalGenres = values().length;
        System.out.println("MovieGenre enum initialized with " + totalGenres + " genres");
    }

    MovieGenre(String displayName, int minimumAge, String description) {
        this.displayName = displayName;
        this.minimumAge = minimumAge;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinimumAge() {
        return minimumAge;
    }

    public String getDescription() {
        return description;
    }

    public boolean suitableForAge(int age) {
        return age >= minimumAge;
    }

    public String getAgeRating() {
        if (minimumAge >= 18) return "FROM-18";
        if (minimumAge >= 16) return "FROM-16";
        if (minimumAge >= 13) return "FROM-13";
        return "FROM TYPE";
    }

    public static int getTotalGenres() {
        return totalGenres;
    }

    public static MovieGenre getGenreByAge(int age) {
        for (MovieGenre genre : values()) {
            if (genre.suitableForAge(age)) {
                return genre;
            }
        }
        return COMEDY;
    }

    public String getFullInfo() {
        return displayName + " (" + getAgeRating() + ") - " + description;
    }
}
