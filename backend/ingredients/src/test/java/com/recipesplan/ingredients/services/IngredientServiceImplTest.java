package com.recipesplan.ingredients.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.mappers.IngredientMapper;
import com.recipesplan.ingredients.repositories.IngredientRepository;
import com.recipesplan.ingredients.services.impl.IngredientServiceImpl;


import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.dtos.Response;

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

    @Test
    public void shouldReturnIngredient_WhenById(){
        Ingredient ingredient = getIngredient();
        //Response<IngredientDto> response = getResponseSuccess(ingredient);
        when(ingredientRepository.findById(ingredient.getId())).thenReturn(Optional.of(ingredient));

        Response<IngredientDto> response = ingredientServiceImpl.getIngredient(ingredient.getId());

        IngredientDto ingredientDto = response.getData();
        assertTrue(ingredientDto.id() == ingredient.getId());

    }

    @Test
    public void shouldSaveIngredient_WhenItsGiven(){
        Ingredient ingredient = getIngredient();
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Response<IngredientDto> response = ingredientServiceImpl.postIngredient(IngredientMapper.toDto(ingredient));

        assertTrue(response.getMeta().getStatusCode().value() == HttpStatus.CREATED.value());
    }

    private Ingredient getIngredient(){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("ingrediente1");
        ingredient.setDescription("Ingrediente de prueba");
        return ingredient;
    }

    private Response getResponseSuccess(Ingredient ingredient){
        Meta meta = new Meta(
            UUID.randomUUID(), 
            HttpStatus.OK, 
            HttpStatusCode.valueOf(HttpStatus.OK.value()),
            new Date()
        );

        Response<IngredientDto> response = new Response<IngredientDto>(
            IngredientMapper.toDto(ingredient), 
            meta);
        return response;
    }
}
