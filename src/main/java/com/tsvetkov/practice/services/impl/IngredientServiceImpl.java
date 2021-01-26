package com.tsvetkov.practice.services.impl;

import com.tsvetkov.practice.Constants;
import com.tsvetkov.practice.exceptions.IngredientAlreadyExistsException;
import com.tsvetkov.practice.exceptions.IngredientDataNotSpecifiedException;
import com.tsvetkov.practice.exceptions.IngredientNotFoundException;
import com.tsvetkov.practice.exceptions.RecipeDataNotSpecifiedException;
import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.parsers.DataParser;
import com.tsvetkov.practice.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private DataParser dataParser;

    @Override
    public Ingredient getIngredient(int ingredientID) throws IOException {
        // Get all ingredients in file
        List<Ingredient> ingredients = dataParser.parseIngredientsFile();

        // Search through them
        for (Ingredient ingredient : ingredients)
            if (ingredient.getIngredientID() == ingredientID)
                return ingredient;

        throw new IngredientNotFoundException("There is no ingredient with ID: " + ingredientID);
    }

    @Override
    public List<Ingredient> getIngredients() throws IOException {
        // Return all ingredients
        return dataParser.parseIngredientsFile();
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) throws IOException {
        // Validate ingredient
        validateIngredient(ingredient);

        // Check if ingredient already exists
        if (ingredientExists(ingredient))
            throw new IngredientAlreadyExistsException("Ingredient with ID " + ingredient.getIngredientID() + " already exists!");

        // Save ingredient
        Files.writeString(Paths.get(Constants.INGREDIENT_FILE), ingredient.toString() + '\n',
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

        return ingredient;
    }

    private void validateIngredient(Ingredient ingredient) {
        if (ingredient == null
                || ingredient.getIngredientID() == 0
                || ingredient.getName() == null
                || ingredient.getOrigin() == null) {
            throw new IngredientDataNotSpecifiedException("Ingredient does not contain all required fields: ingredientID, name and origin.");
        }
    }

    private boolean ingredientExists(Ingredient ingredient) throws IOException {
        List<Ingredient> ingredients = dataParser.parseIngredientsFile();
        for (Ingredient curr : ingredients)
            if (curr.equals(ingredient))
                return true;

        return false;
    }

    @Override
    public Ingredient replaceIngredient(int oldIngredientID, Ingredient newIngredient) throws IOException {
        // Validate ingredient
        validateIngredient(newIngredient);

        // Check if an ingredient with the same ID doesn't exist so that we can legally replace
        if (ingredientExists(newIngredient))
            throw new IngredientAlreadyExistsException("Ingredient with ID " + newIngredient.getIngredientID() + " already exists!");

        // Find old ingredient and remove it, Append the new one
        boolean replacedIngredient = false;
        List<Ingredient> ingredients = dataParser.parseIngredientsFile();
        for (Ingredient curr : ingredients) {
            if (curr.getIngredientID() == oldIngredientID) {
                // Replace old with new ingredient
                curr.setIngredientID(newIngredient.getIngredientID());
                curr.setName(newIngredient.getName());
                curr.setOrigin(newIngredient.getOrigin());
                replacedIngredient = true;
            }
        }

        if (!replacedIngredient)
            // Nothing to replace => bad user input
            throw new IngredientNotFoundException("There is no ingredient with ID: " + oldIngredientID);

        Files.write(Paths.get(Constants.INGREDIENT_FILE),
                ingredients.stream().map(Ingredient::toString).collect(Collectors.toList()));

        return newIngredient;
    }

    @Override
    public Ingredient deleteIngredient(int ingredientID) throws IOException {
        // Find old ingredient and remove it, Append the new one
        boolean deletedIngredient = false;
        int indexOfIngredToDelete = -1;
        List<Ingredient> ingredients = dataParser.parseIngredientsFile();
        for (int i = 0; i < ingredients.size(); i++)
            if (ingredients.get(i).getIngredientID() == ingredientID)
                indexOfIngredToDelete = i;

        Ingredient removedIngredient = null;
        if (indexOfIngredToDelete != -1) {
            removedIngredient = ingredients.remove(indexOfIngredToDelete);
            deletedIngredient = true;
        }

        if (!deletedIngredient)
            // Nothing to replace => bad user input
            throw new IngredientNotFoundException("There is no ingredient with ID: " + ingredientID);

        Files.write(Paths.get(Constants.INGREDIENT_FILE),
                ingredients.stream().map(Ingredient::toString).collect(Collectors.toList()));

        return removedIngredient;
    }
}
