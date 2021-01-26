package com.tsvetkov.practice.services;

import com.tsvetkov.practice.model.Ingredient;

import java.io.IOException;
import java.util.List;

public interface IngredientService {
    Ingredient getIngredient(int ingredientID) throws IOException;

    List<Ingredient> getIngredients() throws IOException;

    Ingredient createIngredient(Ingredient ingredient) throws IOException;

    Ingredient replaceIngredient(int ingredientID, Ingredient ingredient) throws IOException;

    Ingredient deleteIngredient(int ingredientID) throws IOException;
}
