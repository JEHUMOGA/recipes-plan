package com.recipesplan.ingredients.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.dtos.Response;
import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.mappers.IngredientMapper;
import com.recipesplan.ingredients.repositories.IngredientRepository;
import com.recipesplan.ingredients.services.IngredientService;

import tools.jackson.databind.ObjectMapper;

@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, IngredientMapper ingredientMapper){
        this.ingredientRepository = ingredientRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Response<IngredientDto> findById(Long ingredientId) {
    
        Optional<Ingredient> optional = ingredientRepository.findById(ingredientId);
        if(!optional.isPresent()){
            return getResponseNotFound();
        }

        return getResponseSuccess(optional.get());
    }

    private Response<IngredientDto> getResponseSuccess(Ingredient ingredient){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.OK, 
            HttpStatusCode.valueOf(HttpStatus.OK.value()),
            new Date()
        );

        Response<IngredientDto> response = new Response<IngredientDto>(
            ingredientMapper.toDto(ingredient), 
            meta);
        return response;
    }

    private Response getResponseNotFound(){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.NOT_FOUND, 
            HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()),
            new Date()
        );
        
        Response<IngredientDto> response = new Response<IngredientDto>(
            null, 
            meta);
        return response;
    }
    
}
