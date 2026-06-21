package com.recipesplan.ingredients.dtos;

import lombok.Data;

@Data
public class Response<T> {
    T data;
    Meta meta;
}
