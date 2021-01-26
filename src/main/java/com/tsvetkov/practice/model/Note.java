package com.tsvetkov.practice.model;

import java.util.Objects;

public class Note {
    private int number;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return number == note.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    public Note(int number, String description) {
        this.number = number;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return number + "," + description;
    }
}
