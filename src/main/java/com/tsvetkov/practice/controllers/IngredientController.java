package com.tsvetkov.practice.controllers;

import com.tsvetkov.practice.model.Ingredient;
import com.tsvetkov.practice.services.IngredientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Api(tags = { "Ingredients" })
@SwaggerDefinition(tags = { @Tag(name = "Ingredients Controller", description = "Ingredients CRUD Operations") })
@RequestMapping("api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @ApiOperation(value = "Returns specified ingredient.", response = Ingredient.class)
    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") int ingredientID) throws IOException {
        return new ResponseEntity<>(ingredientService.getIngredient(ingredientID), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns all ingredients.", response = List.class)
    @GetMapping
    public ResponseEntity<List<Ingredient>> getIngredients() throws IOException {
        return new ResponseEntity<>(ingredientService.getIngredients(), HttpStatus.OK);
    }

    @ApiOperation(value = "Creates specified ingredient.", response = Ingredient.class)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Ingredient> createRecipe(@RequestBody Ingredient ingredient) throws IOException {
        return new ResponseEntity<>(ingredientService.createIngredient(ingredient), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Replaces specified ingredient.", response = Ingredient.class)
    @PutMapping(path = "{id}")
    public ResponseEntity<Ingredient> replaceIngredient(@PathVariable("id") int ingredientID, @RequestBody Ingredient ingredient) throws IOException {
        return new ResponseEntity<>(ingredientService.replaceIngredient(ingredientID, ingredient), HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes specified ingredient.", response = Ingredient.class)
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") int ingredientID) throws IOException {
        return new ResponseEntity<>(ingredientService.deleteIngredient(ingredientID), HttpStatus.OK);
    }

}
