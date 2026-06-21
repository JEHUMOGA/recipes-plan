package com.recipesplan.ingredients.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.repositories.IngredientRepository;
import com.recipesplan.ingredients.services.impl.IngredientServiceImpl;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceImplTest {
    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientServiceImpl ingredientServiceImpl;

    @Test
    public void getList(){
        Ingredient ingredient = getIngredient();
        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));

        List<Ingredient> response = ingredientServiceImpl.getAll();

        assertTrue(response.size() == 1);
        assertTrue(response.get(0).getName().equalsIgnoreCase(ingredient.getName()));
    }

    private Ingredient getIngredient(){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("ingrediente1");
        ingredient.setDescription("Ingrediente de prueba");
        return ingredient;
    }
}
