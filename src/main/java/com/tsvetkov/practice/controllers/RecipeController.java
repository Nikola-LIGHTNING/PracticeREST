package com.tsvetkov.practice.controllers;

import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.model.Recipe;
import com.tsvetkov.practice.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(path = "{recipeTitle}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("recipeTitle") String recipeTitle) throws IOException {
        return new ResponseEntity<>(recipeService.getRecipe(recipeTitle), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Recipe>> getRecipes() throws IOException {
        return new ResponseEntity<>(recipeService.getRecipes(), HttpStatus.OK);
    }

    @GetMapping(path = "{recipeTitle}/ingredients")
    public ResponseEntity<List<Ingredient>> getRecipeIngredients(@PathVariable("recipeTitle") String recipeTitle) throws IOException {
        return new ResponseEntity<>(recipeService.getIngredientsInRecipe(recipeTitle), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) throws IOException {
        return new ResponseEntity<>(recipeService.createRecipe(recipe), HttpStatus.CREATED);
    }

    @PutMapping(path = "{recipeTitle}")
    public ResponseEntity<Recipe> replaceRecipe(@PathVariable("recipeTitle") String recipeTitle, @RequestBody Recipe recipe) throws IOException {
        return new ResponseEntity<>(recipeService.replaceRecipe(recipeTitle, recipe), HttpStatus.OK);
    }

    @DeleteMapping(path = "{recipeTitle}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable("recipeTitle") String recipeTitle) throws IOException {
        return new ResponseEntity<>(recipeService.deleteRecipe(recipeTitle), HttpStatus.OK);
    }


}
