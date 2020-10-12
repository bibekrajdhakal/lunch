package com.lunch.dto;

import com.lunch.model.Ingredients;
import com.lunch.model.Recipes;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class RecipesResourceTest {

    @Test
    public void testFrom() {
        Ingredients ingredients = new Ingredients();
        ingredients.setTitle("ingredients title");
        ingredients.setBestBefore(Date.from(LocalDate.of(2020, 10, 13).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        ingredients.setUseBy(Date.from(LocalDate.of(2020, 10, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        Recipes recipes = new Recipes();
        recipes.setTitle("title");
        recipes.setIngredients(Collections.singleton(ingredients));

        RecipesResource resource = RecipesResource.from(recipes);

        assertThat(resource.getTitle(), is(equalTo("title")));

        List<IngredientsResource> ingredientsResourceList = resource.getIngredientsResources();
        assertThat(ingredientsResourceList.size(), is(equalTo(1)));

        IngredientsResource ingredientsResource = ingredientsResourceList.get(0);
        assertThat(ingredientsResource.getTitle(), is(equalTo("ingredients title")));
        assertThat(ingredientsResource.getBestBefore(), is(equalTo(Date.from(LocalDate.of(2020, 10, 13).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
        assertThat(ingredientsResource.getUseBy(), is(equalTo(Date.from(LocalDate.of(2020, 10, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
    }

}
