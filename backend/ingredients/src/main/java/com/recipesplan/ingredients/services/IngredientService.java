package com.recipesplan.ingredients.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.dtos.Response;
import com.recipesplan.ingredients.entities.Ingredient;

@Service
public interface IngredientService {
    public List<Ingredient> getAll();
    public Response<IngredientDto> getIngredient(Long ingredientId);
    public Response<IngredientDto> postIngredient(IngredientDto ingredientDto);
    public Response<IngredientDto> updateIngredient(Long ingredientId, IngredientDto ingredientDto);
    public Response<IngredientDto> deleteIngredient(Long ingredientId);
}
