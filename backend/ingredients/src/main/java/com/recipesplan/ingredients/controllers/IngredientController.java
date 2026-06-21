package com.recipesplan.ingredients.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.dtos.Response;
import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.services.IngredientService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("/v1/")
    public ResponseEntity<?> getAll() {
        Response<List<Ingredient>> response = new Response<>();
        response.setData(ingredientService.getAll());
        response.setMeta(new Meta());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
