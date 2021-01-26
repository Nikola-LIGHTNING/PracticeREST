package com.tsvetkov.practice.services;

import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.model.Recipe;

import java.io.IOException;
import java.util.List;

public interface RecipeService {
    Recipe getRecipe(String title) throws IOException;

    List<Recipe> getRecipes() throws IOException;

    List<Ingredient> getIngredientsInRecipe(String title) throws IOException;

    Recipe createRecipe(Recipe recipe) throws IOException;

    Recipe replaceRecipe(String title, Recipe Recipe) throws IOException;

    Recipe deleteRecipe(String title) throws IOException;
}
