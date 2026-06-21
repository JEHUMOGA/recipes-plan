package com.recipesplan.ingredients.dtos;

import java.util.Date;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private UUID transactionId = UUID.randomUUID();
    private String status;
    private Integer statusCode;
    private Date timestamp = new Date();
}
