package com.recipesplan.ingredients.mappers;

import org.springframework.stereotype.Component;

import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.entities.Ingredient;

@Component
public final class IngredientMapper {

    public static IngredientDto toDto(Ingredient ingredient){
        if(ingredient == null) return null;
        return new IngredientDto(
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getDescription()
        );
    }

    public static Ingredient toEntity(IngredientDto ingredientDto){
        if(ingredientDto == null) return null;
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDto.id());
        ingredient.setName(ingredientDto.name());
        ingredient.setDescription(ingredientDto.description());
        return ingredient;
    }
}
