package com.recipesplan.ingredients.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.dtos.Response;
import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.services.IngredientService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("/v1/")
    public ResponseEntity<?> getAll() {
        
        Meta meta = new Meta(
            UUID.randomUUID(),
            HttpStatus.OK,
            HttpStatusCode.valueOf(HttpStatus.OK.value()), 
            new Date()
        );

        Response<List<Ingredient>> response = new Response<>(
            ingredientService.getAll(),
            meta);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable Long ingredientId) {
        return new ResponseEntity<>(ingredientService.findById(ingredientId), HttpStatus.OK);
    }
    
    
}
