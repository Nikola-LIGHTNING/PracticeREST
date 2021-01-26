package com.tsvetkov.practice.controllers;

import com.tsvetkov.practice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is where all the exceptions are being passed after they are thrown. We can create methods annotated
 * with @ExceptionHandler where we "catch" one or multiple types of exceptions and manage them from a single place.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    // Handle exceptions which are thrown because a resource is not found. (404)
    @ExceptionHandler(value = {IngredientNotFoundException.class, RecipeNotFoundException.class})
    private ResponseEntity<String> handleNotFound(RuntimeException ex) {
        return new ResponseEntity<>(ex.getClass().getSimpleName() + ": " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Handle exceptions which are thrown because a resource already exists. (409)
    @ExceptionHandler(value = {IngredientAlreadyExistsException.class, RecipeAlreadyExistsException.class})
    private ResponseEntity<String> handleAlreadyExists(RuntimeException ex) {
        return new ResponseEntity<>(ex.getClass().getSimpleName() + ": " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle exceptions which are thrown when the request does not contain all the needed data. (409)
    @ExceptionHandler(value = {IngredientDataNotSpecifiedException.class, RecipeDataNotSpecifiedException.class})
    private ResponseEntity<String> handleLackOfData(RuntimeException ex) {
        return new ResponseEntity<>(ex.getClass().getSimpleName() + ": " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Handle exceptions which are thrown because of internal reasons and the user has no control over.
    // For example, such exceptions would be thrown for unsuccessful deletion of a resource, because of a IO problem. (500)
    @ExceptionHandler(value = {ServiceException.class})
    private ResponseEntity<String> handleServiceException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


}