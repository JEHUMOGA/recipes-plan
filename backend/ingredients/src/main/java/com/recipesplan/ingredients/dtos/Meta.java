package com.recipesplan.ingredients.dtos;

import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Meta {
    private UUID transactionId = UUID.randomUUID();
    private HttpStatus status;
    private HttpStatusCode statusCode;
    private Date timestamp = new Date();
}
