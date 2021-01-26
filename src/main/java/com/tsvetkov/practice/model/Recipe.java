package com.tsvetkov.practice.model;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Recipe {
    private String title;
    private String author;
    private Set<Integer> ingredients;
    private List<Note> notes;
    private double price;

    public Recipe(String title, String author, Set<Integer> ingredients, List<Note> notes, double price) {
        this.title = title;
        this.author = author;
        this.ingredients = ingredients;
        this.notes = notes;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(title, recipe.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        // Recipe as csv
        return title + ","
                + author + ","
                + price
                + "|" + ingredients.stream().map((val) -> "" + val).reduce((str1, str2) -> str1 + "," + str2).get()
                + "|" + notes.stream().map(Note::toString).reduce((str1, str2) -> str1 + "," + str2).get();

    }
}
