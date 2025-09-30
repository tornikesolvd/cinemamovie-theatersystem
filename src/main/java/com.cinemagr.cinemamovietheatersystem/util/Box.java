package  com.cinemagr.cinemamovietheatersystem.util;

public class Box<T> {

    private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public boolean empty() {
        return value == null;
    }
}


