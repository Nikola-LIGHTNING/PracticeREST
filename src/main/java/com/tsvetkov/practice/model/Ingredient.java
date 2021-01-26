package com.tsvetkov.practice.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class Ingredient {

    @ApiModelProperty(notes = "The id of the ingredient. Must be unique.", name = "IngredientID", required = true, value = "1")
    private int ingredientID;

    @ApiModelProperty(notes = "The name of the ingredient.", name = "Name", required = true, value = "Banana")
    private String name;

    @ApiModelProperty(notes = "The origin of the ingredient.", name = "Origin", required = true, value = "Bulgaria")
    private String origin;

    public Ingredient(int ingredientID, String name, String origin) {
        this.ingredientID = ingredientID;
        this.name = name;
        this.origin = origin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredientID, that.ingredientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientID);
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        // Ingredient as csv
        return ingredientID + ","
                + name + ","
                + origin;
    }
}
