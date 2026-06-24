package com.recipesplan.ingredients.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipesplan.ingredients.dtos.IngredientDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService;
    }

    @GetMapping("/v1")
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

    @GetMapping("/find/v1/{ingredientId}")
    public ResponseEntity<?> findById(@PathVariable Long ingredientId) {
        Response<IngredientDto> response = ingredientService.getIngredient(ingredientId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1")
    public ResponseEntity<?> postIngredient(@RequestBody IngredientDto dto) {
        Response<IngredientDto> response = ingredientService.postIngredient(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @PutMapping("/v1/{ingredientId}")
    public ResponseEntity<?> putIngredient(@PathVariable Long ingredientId, @RequestBody IngredientDto dto) {
        Response<IngredientDto> response = ingredientService.updateIngredient(ingredientId, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/v1/{ingredientId}")
    public ResponseEntity<?> deleteIngredient(@PathVariable Long ingredientId){
        Response<IngredientDto> response = ingredientService.deleteIngredient(ingredientId);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    
}
