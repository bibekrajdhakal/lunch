package com.lunch.dto;

import com.lunch.model.Recipes;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RecipesResource {

    private String title;
    private List<IngredientsResource> ingredientsResources;

    public static RecipesResource from(Recipes recipes) {
        RecipesResource recipesResource = new RecipesResource();
        recipesResource.title = recipes.getTitle();
        recipesResource.ingredientsResources = recipes.getIngredients().stream()
                .map(IngredientsResource::from)
                .collect(Collectors.toList());

        return recipesResource;
    }

}
