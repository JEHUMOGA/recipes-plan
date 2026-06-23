package com.recipesplan.ingredients.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response<T> {
    T data;
    Meta meta;
}
