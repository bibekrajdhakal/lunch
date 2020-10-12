package com.lunch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lunch.model.Ingredients;
import lombok.Data;

import java.util.Date;

@Data
public class IngredientsResource {

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private Date bestBefore;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd")
    private Date useBy;

    public static IngredientsResource from(Ingredients ingredients) {
        IngredientsResource ingredientsResource = new IngredientsResource();
        ingredientsResource.setTitle(ingredients.getTitle());
        ingredientsResource.setBestBefore(ingredients.getBestBefore());
        ingredientsResource.setUseBy(ingredients.getUseBy());

        return ingredientsResource;
    }

}
