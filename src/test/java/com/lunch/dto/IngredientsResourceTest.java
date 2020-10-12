package com.lunch.dto;

import com.lunch.model.Ingredients;
import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class IngredientsResourceTest {

    @Test
    public void testFrom() {
        Ingredients ingredients = new Ingredients();
        ingredients.setTitle("title");
        ingredients.setBestBefore(Date.from(LocalDate.of(2020, 10, 13).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        ingredients.setUseBy(Date.from(LocalDate.of(2020, 10, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        IngredientsResource resource = IngredientsResource.from(ingredients);

        assertThat(resource.getTitle(), is(equalTo("title")));
        assertThat(resource.getBestBefore(), is(equalTo(Date.from(LocalDate.of(2020, 10, 13).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
        assertThat(resource.getUseBy(), is(equalTo(Date.from(LocalDate.of(2020, 10, 18).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()))));
    }

}
