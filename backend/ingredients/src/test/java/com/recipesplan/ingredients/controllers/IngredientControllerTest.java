package com.recipesplan.ingredients.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Test
    public void shouldReturnIngredients_WhenGetAllIngredients() throws Exception{
        Ingredient ingredient = getIngredient();
        when(ingredientService.getAll()).thenReturn(List.of(ingredient));

        mockMvc.perform(get("/api/ingredient/v1")
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnIngredient_WhenFindById(){
        try {
            Ingredient ingredient = getIngredient();
            Response<IngredientDto> response = getResponseSuccess(ingredient);
            Long ingredientId = ingredient.getId();
            when(ingredientService.getIngredient(ingredientId)).thenReturn(response);

            mockMvc.perform(get("/api/ingredient/find/v1/{ingredientId}",1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        } catch (Exception e) {
            e.getStackTrace();
        }
         
    }

    @Test
    public void shouldReturnIngredient_WhenItsSave() throws Exception{
        Ingredient ingredient = getIngredient();
        Response<IngredientDto> response = getResponseSuccess(ingredient);
        when(ingredientService.postIngredient(IngredientMapper.toDto(ingredient))).thenReturn(response);

        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(ingredient);
     
        mockMvc.perform(post("/api/ingredient/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(input)
        ).andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdateIngredient_WhenItsPut() throws Exception{
        Ingredient ingredient = getIngredient();
        Response<IngredientDto> response = getResponseSuccess(ingredient);

        when(ingredientService.updateIngredient(ingredient.getId(),IngredientMapper.toDto(ingredient))).thenReturn(response);
        ObjectMapper objectMapper = new ObjectMapper();
        String input = objectMapper.writeValueAsString(ingredient);

        mockMvc.perform(put("/api/ingredient/v1/{ingredientId}", ingredient.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(input)
        ).andExpect(status().isOk());
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
            IngredientMapper.toDto(ingredient), 
            meta);
        return response;
    }
    
}
