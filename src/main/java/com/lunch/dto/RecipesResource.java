package com.lunch.dto;

import com.lunch.model.Recipes;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Resource class for recipes to be passed as data transfer object
 */
@Data
public class RecipesResource {

    private String title;
    private List<IngredientsResource> ingredientsResources;

    /**
     * convert recipes to resource
     * @param recipes the entity model recipes
     * @return the resource
     */
    public static RecipesResource from(Recipes recipes) {
        RecipesResource recipesResource = new RecipesResource();
        recipesResource.title = recipes.getTitle();
        recipesResource.ingredientsResources = recipes.getIngredients().stream()
                .map(IngredientsResource::from)
                .collect(Collectors.toList());

        return recipesResource;
    }

}
