package com.lunch.dto;

import lombok.Data;

import java.util.List;

/**
 * wrapper class for a list of recipe resources
 */
@Data
public class RecipesList {

    private List<RecipesResource> recipesResources;

}
