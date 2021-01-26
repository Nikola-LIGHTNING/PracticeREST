package com.tsvetkov.practice.parsers;

import com.tsvetkov.practice.Constants;
import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.model.Note;
import com.tsvetkov.practice.model.Recipe;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataParser {

    public List<Ingredient> parseIngredientsFile() throws IOException {
        List<Ingredient> ingredients = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.INGREDIENT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line
                ingredients.add(parseIngredient(line));
            }
        }

        return ingredients;
    }

    private Ingredient parseIngredient(String line) {
        String[] fields = line.split(",");
        int ingredientID = Integer.parseInt(fields[0]);
        String name = fields[1];
        String origin = fields[2];

        return new Ingredient(ingredientID, name, origin);
    }

    public List<Recipe> parseRecipesFile() throws IOException {
        List<Recipe> recipes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.RECIPE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line
                recipes.add(parseRecipe(line));
            }
        }

        return recipes;
    }

    private Recipe parseRecipe(String line) {
        String[] fieldsBig = line.split("\\|");
        String[] fields = fieldsBig[0].split(",");
        String name = fields[0];
        String author = fields[1];
        double price = Double.parseDouble(fields[2]);

        Set<Integer> ingredients = Arrays.stream(fieldsBig[1].split(",")).map(Integer::parseInt).collect(Collectors.toSet());

        String[] noteFields = fieldsBig[2].split(",");
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < noteFields.length; i += 2)
            notes.add(new Note(Integer.parseInt(noteFields[i]), noteFields[i+1]));

        return new Recipe(name, author, ingredients, notes, price);
    }

}
