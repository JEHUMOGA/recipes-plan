package com.recipesplan.ingredients.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.recipesplan.ingredients.entities.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long>{
    
}
