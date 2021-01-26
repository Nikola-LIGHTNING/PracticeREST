package com.tsvetkov.practice.services.impl;

import com.tsvetkov.practice.Constants;
import com.tsvetkov.practice.exceptions.RecipeAlreadyExistsException;
import com.tsvetkov.practice.exceptions.RecipeDataNotSpecifiedException;
import com.tsvetkov.practice.exceptions.RecipeNotFoundException;
import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.model.Recipe;
import com.tsvetkov.practice.parsers.DataParser;
import com.tsvetkov.practice.services.IngredientService;
import com.tsvetkov.practice.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private DataParser dataParser;

    @Autowired
    private IngredientService ingredientService;

    @Override
    public Recipe getRecipe(String title) throws IOException {
        // Get all recipes in file
        List<Recipe> recipes = dataParser.parseRecipesFile();

        // Search through them
        for (Recipe recipe : recipes)
            if (recipe.getTitle().equals(title))
                return recipe;

        throw new RecipeNotFoundException("There is no recipe with name: " + title);
    }

    @Override
    public List<Recipe> getRecipes() throws IOException {
        // Return all recipes
        return dataParser.parseRecipesFile();
    }

    @Override
    public List<Ingredient> getIngredientsInRecipe(String title) throws IOException {
        Recipe recipe = getRecipe(title);
        Set<Integer> ingrIDs = recipe.getIngredients();
        List<Ingredient> ingredients = new ArrayList<>();

        // Possibly throw exception explaining this recipe contains ingredients that do not exist
        for (int id : ingrIDs)
            ingredients.add(ingredientService.getIngredient(id));

        return ingredients;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) throws IOException {
        // Validate recipe
        validateRecipe(recipe);

        // Check if recipe already exists
        if (recipeExists(recipe))
            throw new RecipeAlreadyExistsException("Recipe with title " + recipe.getTitle() + " already exists!");

        // Save ingredient
        Files.writeString(Paths.get(Constants.RECIPE_FILE), recipe.toString() + '\n',
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        return recipe;
    }

    private void validateRecipe(Recipe recipe) {
        if (recipe == null
                || recipe.getTitle() == null
                || recipe.getAuthor() == null
                || recipe.getIngredients() == null
                || recipe.getNotes() == null) {
            throw new RecipeDataNotSpecifiedException("Recipe does not contain all required fields: title, author, ingredients and notes");
        }
    }

    private boolean recipeExists(Recipe recipe) throws IOException {
        List<Recipe> recipes = dataParser.parseRecipesFile();
        for (Recipe curr : recipes)
            if (curr.equals(recipe))
                return true;

        return false;
    }

    @Override
    public Recipe replaceRecipe(String oldRecipeTitle, Recipe newRecipe) throws IOException {
        // Validate recipe
        validateRecipe(newRecipe);

        // Check if an recipe with the same title doesn't exist so that we can legally replace
        if (recipeExists(newRecipe))
            throw new RecipeAlreadyExistsException("Recipe with title " + newRecipe.getTitle() + " already exists!");

        // Find old recipe and remove it, Append the new one
        boolean replacedRecipe = false;
        List<Recipe> recipes = dataParser.parseRecipesFile();
        for (Recipe curr : recipes) {
            if (curr.getTitle().equals(oldRecipeTitle)) {
                // Replace old with new recipe
                curr.setTitle(newRecipe.getTitle());
                curr.setAuthor(newRecipe.getAuthor());
                curr.setIngredients(newRecipe.getIngredients());
                curr.setNotes(newRecipe.getNotes());
                curr.setPrice(newRecipe.getPrice());
                replacedRecipe = true;
            }
        }

        if (!replacedRecipe)
            // Nothing to replace => bad user input
            throw new RecipeNotFoundException("There is no recipe with title: " + oldRecipeTitle);

        Files.write(Paths.get(Constants.RECIPE_FILE),
                recipes.stream().map(Recipe::toString).collect(Collectors.toList()));

        return newRecipe;
    }

    @Override
    public Recipe deleteRecipe(String title) throws IOException {
        // Find old recipe and remove it, Append the new one
        boolean deletedRecipe = false;
        int indexOfRecipeToDelete = -1;
        List<Recipe> recipes = dataParser.parseRecipesFile();
        for (int i = 0; i < recipes.size(); i++)
            if (recipes.get(i).getTitle().equals(title))
                indexOfRecipeToDelete = i;

        Recipe removedRecipe = null;
        if (indexOfRecipeToDelete != -1) {
            removedRecipe = recipes.remove(indexOfRecipeToDelete);
            deletedRecipe = true;
        }

        if (!deletedRecipe)
            // Nothing to replace => bad user input
            throw new RecipeNotFoundException("There is no recipe with title: " + title);

        Files.write(Paths.get(Constants.RECIPE_FILE),
                recipes.stream().map(Recipe::toString).collect(Collectors.toList()));

        return removedRecipe;
    }
}
