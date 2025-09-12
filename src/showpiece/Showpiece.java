package showpiece;

import java.util.Objects;

public abstract class Showpiece {
    protected Integer id;

    public Showpiece() {
    }

    public Showpiece(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Showpiece entity)) return false;
        return Objects.equals(id, entity.id);
    }
}
