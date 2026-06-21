package com.recipesplan.ingredients.mappers;

import org.springframework.stereotype.Component;

import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.entities.Ingredient;

@Component
public class IngredientMapper {
    private final IngredientDto ingredientDto;

    IngredientMapper(IngredientDto ingredientDto) {
        this.ingredientDto = ingredientDto;
    }

    public IngredientDto toDto(Ingredient ingredient){
        if(ingredient == null) return null;
        return new IngredientDto(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getDescription()
        );
    }
}
