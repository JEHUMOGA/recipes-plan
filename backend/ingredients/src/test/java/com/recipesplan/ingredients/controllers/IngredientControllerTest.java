package com.recipesplan.ingredients.controllers;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.recipesplan.ingredients.dtos.IngredientDto;
import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.dtos.Response;
import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.mappers.IngredientMapper;
import com.recipesplan.ingredients.services.IngredientService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Test
    public void shouldReturnIngredients_WhenGetAllIngredients() throws Exception{
        Ingredient ingredient = getIngredient();
        when(ingredientService.getAll()).thenReturn(List.of(ingredient));

        mockMvc.perform(get("/api/ingredient/v1/")
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnIngredient_WhenFindById() throws Exception{
        IngredientDto ingredientDto = ingredientMapper.toDto(getIngredient());
        Response<IngredientDto> response = getResponseSuccess(getIngredient());
        Long ingredientId = ingredientDto.id();
        when(ingredientService.findById(ingredientId)).thenReturn(response);

        mockMvc.perform(get("/api/ingredient/v1/{}", ingredientDto.id())
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());   
    }

    private Ingredient getIngredient(){
        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("ingrediente1");
        ingredient.setDescription("Ingrediente de prueba");
        return ingredient;
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
    
}
