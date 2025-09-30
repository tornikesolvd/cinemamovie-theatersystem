package com.cinemagr.cinemamovietheatersystem.showpiece;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Showpiece {
    protected Integer id;
    protected String name;
    protected String description;
    protected boolean active;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String updatedBy;

    {
        createdAt = LocalDateTime.now();
        active = true;
    }

    public Showpiece() {
    }

    public Showpiece(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    protected void touchHelper() {
        this.updatedAt = LocalDateTime.now();
    }

    public abstract boolean isValid();

    public abstract String getDisplayname();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, active);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Showpiece entity)) return false;
        return Objects.equals(id, entity.id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [id=" + id +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt + "]";
    }
}
