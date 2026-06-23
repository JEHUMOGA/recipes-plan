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


@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Response<IngredientDto> getIngredient(Long ingredientId) {
    
        Optional<Ingredient> optional = ingredientRepository.findById(ingredientId);
        if(!optional.isPresent()){
            return getResponseNotFound();
        }

        return getResponseSuccess(optional.get());
    }

    @Override
    public Response<IngredientDto> postIngredient(IngredientDto ingredientDto) {
        if(ingredientDto == null)
            return getResponseBadRequest();

        Ingredient response = ingredientRepository.save(IngredientMapper.toEntity(ingredientDto));
        return getResponseIsCreated();
    }

    private Response<IngredientDto> getResponseSuccess(Ingredient ingredient){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.OK, 
            HttpStatusCode.valueOf(HttpStatus.OK.value()),
            new Date()
        );
        IngredientDto ingredientDto = IngredientMapper.toDto(ingredient);
        Response<IngredientDto> response = new Response<IngredientDto>(
            ingredientDto, 
            meta);
        return response;
    }

    private Response<IngredientDto> getResponseIsCreated(){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.CREATED, 
            HttpStatusCode.valueOf(HttpStatus.CREATED.value()),
            new Date()
        );
        IngredientDto ingredientDto = null;
        Response<IngredientDto> response = new Response<IngredientDto>(
            ingredientDto, 
            meta);
        return response;
    }

    private Response<IngredientDto> getResponseNotFound(){
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

    private Response<IngredientDto> getResponseBadRequest(){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.BAD_REQUEST, 
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            new Date()
        );
        
        Response<IngredientDto> response = new Response<IngredientDto>(
            null, 
            meta);
        return response;
    }
    
}
