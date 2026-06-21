package com.recipesplan.ingredients.controllers;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.recipesplan.ingredients.dtos.Meta;
import com.recipesplan.ingredients.entities.Ingredient;
import com.recipesplan.ingredients.services.IngredientService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

        mockMvc.perform(get("/api/ingredient/v1/")
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

    private Meta getMeta(){
        Meta meta = new Meta();
        meta.setTransactionId(UUID.fromString("12345"));
        meta.setStatus("OK");
        meta.setStatusCode(200);

        return meta;
    }
    
}
