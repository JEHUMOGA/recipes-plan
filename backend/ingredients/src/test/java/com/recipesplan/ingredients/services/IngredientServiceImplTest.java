package com.recipesplan.ingredients.services;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
    public void shouldReturnNotFound_WhenDoesntFoundIngredient(){
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());

        Response<IngredientDto> response = ingredientServiceImpl.getIngredient(1L);

        assertTrue(response.getMeta().getStatus() == HttpStatus.NOT_FOUND);
        
    }

    @Test
    public void shouldSaveIngredient_WhenItsGiven(){
        Ingredient ingredient = getIngredient();
        when(ingredientRepository.save(ingredient)).thenReturn(ingredient);

        Response<IngredientDto> response = ingredientServiceImpl.postIngredient(IngredientMapper.toDto(ingredient));

        assertTrue(response.getMeta().getStatusCode().value() == HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnBadRequest_WhenIngredientProviderIsNull(){
        Response<IngredientDto> response = ingredientServiceImpl.postIngredient(null);

        assertTrue(response.getMeta().getStatus() == HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldUpdateIngredient_WhenItsGiven(){
        Ingredient ingredient1 = getIngredient();
        Ingredient ingredient2 = getIngredient();
        ingredient2.setName("Test Update");
        when(ingredientRepository.findById(ingredient1.getId())).thenReturn(Optional.of(ingredient1));
        when(ingredientRepository.save(ingredient2)).thenReturn(ingredient2);

        Response<IngredientDto> response = ingredientServiceImpl.updateIngredient(ingredient2.getId(), IngredientMapper.toDto(ingredient2));

        assertTrue(response.getData().name().equalsIgnoreCase(ingredient2.getName()));

    }

    @Test
    public void shouldReturnNotFound_WhenUpdateNotFoundIngredient(){
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Response<IngredientDto> response = ingredientServiceImpl.updateIngredient(1L, any());

        assertTrue(response.getMeta().getStatus() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldDeleteIngredient_WhenIdItsGiven(){
        Ingredient ingredient = getIngredient();
        when(ingredientRepository.findById(ingredient.getId())).thenReturn(Optional.of(ingredient));
        doNothing().when(ingredientRepository).delete(ingredient);

        ingredientServiceImpl.deleteIngredient(ingredient.getId());

        verify(ingredientRepository, times(1)).findById(ingredient.getId());
        verify(ingredientRepository, times(1)).delete(ingredient);
    }

    @Test
    public void DontDelete_WhenUpdateNotFoundIngredient(){
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Response<IngredientDto> response = ingredientServiceImpl.deleteIngredient(1L);

        assertTrue(response.getMeta().getStatus() == HttpStatus.NOT_FOUND);
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
