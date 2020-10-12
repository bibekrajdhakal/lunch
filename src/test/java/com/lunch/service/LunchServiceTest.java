package com.lunch.service;

import com.lunch.dto.RecipesList;
import com.lunch.model.Ingredients;
import com.lunch.model.Recipes;
import com.lunch.repository.RecipesRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class LunchServiceTest {

    @Mock
    private LunchServiceImpl service;

    @Mock
    private RecipesRepository recipesRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new LunchServiceImpl(recipesRepository);
    }

    @Test
    public void testGet() {
        List<Recipes> recipesList = new ArrayList<>();

        Set<Ingredients> ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 1", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 2", -3, 7));
        recipesList.add(createRecipes("title 1", ingredientsSet));

        ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 3", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 4", 3, 6));
        recipesList.add(createRecipes("title 2", ingredientsSet));

        ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 5", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 6", -10, -6));
        recipesList.add(createRecipes("title 3", ingredientsSet));

        when(recipesRepository.findAll()).thenReturn(recipesList);
        RecipesList list = service.get();

        assertNotNull(list);
        assertThat(list.getRecipesResources().size(), is(equalTo(2)));
        assertThat(list.getRecipesResources().get(0).getTitle(), is(equalTo("title 2")));
        assertThat(list.getRecipesResources().get(1).getTitle(), is(equalTo("title 1")));
    }

    @Test
    public void testIsWithInUseByDateWhenAllIngredientsUseByDateIsBeforeShouldReturnTrue() {
        Recipes recipes = new Recipes();
        recipes.setTitle("title");

        Set<Ingredients> ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 1", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 2", 3, 7));
        recipes.setIngredients(ingredientsSet);

        boolean useByDate = service.isWithInUseByDate(recipes);
        assertTrue("All ingredients best before date is after current date so should be true", useByDate);
    }

    @Test
    public void testIsWithInUseByDateWhenSomeIngredientsUseByDateIsAfterShouldReturnFalse() {
        Recipes recipes = new Recipes();
        recipes.setTitle("title");

        Set<Ingredients> ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 1", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 2", -15, -7));
        recipes.setIngredients(ingredientsSet);

        boolean useByDate = service.isWithInUseByDate(recipes);
        assertFalse("Some ingredients best before date is before current date so should be false", useByDate);
    }

    @Test
    public void testIsWithInBestBeforeDateWhenAllIngredientsBestBeforeDateIsBeforeShouldReturnTrue() {
        Recipes recipes = new Recipes();
        recipes.setTitle("title");

        Set<Ingredients> ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 1", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 2", 3, 7));
        recipes.setIngredients(ingredientsSet);

        boolean bestBefore = service.isWithInBestBeforeDate(recipes);
        assertTrue("All ingredients best before date is after current date so should be true", bestBefore);
    }

    @Test
    public void testIsWithInBestBeforeDateWhenSomeIngredientsBestBeforeDateIsAfterShouldReturnFalse() {
        Recipes recipes = new Recipes();
        recipes.setTitle("title");

        Set<Ingredients> ingredientsSet = new HashSet<>();
        ingredientsSet.add(createIngredient("ingredients title 1", 3, 7));
        ingredientsSet.add(createIngredient("ingredients title 2", -15, -7));
        recipes.setIngredients(ingredientsSet);

        boolean bestBefore = service.isWithInBestBeforeDate(recipes);
        assertFalse("Some ingredients best before date is before current date so should be false", bestBefore);
    }

    @Test
    public void testIsFirstDateBeforeSecondDateWhenSecondDateIsAFutureDateShouldReturnTrue() {
        boolean firstDateBeforeSecondDate = service.isFirstDateBeforeSecondDate(LocalDate.now(),
                Date.from(LocalDate.now().plusDays(7).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertTrue("Second date is 7 days after so should be true", firstDateBeforeSecondDate);
    }

    @Test
    public void testIsFirstDateBeforeSecondDateWhenSecondDateIsAPastDateShouldReturnFalse() {
        boolean firstDateBeforeSecondDate = service.isFirstDateBeforeSecondDate(LocalDate.now(),
                Date.from(LocalDate.now().minusDays(7).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertFalse("Second date is 7 days before so should be false", firstDateBeforeSecondDate);
    }

    @Test
    public void testIsFirstDateBeforeSecondDateWhenBothDatesAreSameShouldReturnTrue() {
        boolean firstDateBeforeSecondDate = service.isFirstDateBeforeSecondDate(LocalDate.now(),
                Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        assertTrue("Second date is the same day as first date so should be true", firstDateBeforeSecondDate);
    }

    private Ingredients createIngredient(String title, int bestBeforeDays, int useByDays) {
        Ingredients ingredients = new Ingredients();
        ingredients.setTitle(title);
        ingredients.setBestBefore(Date.from(LocalDate.now().plusDays(bestBeforeDays).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        ingredients.setUseBy(Date.from(LocalDate.now().plusDays(useByDays).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        return ingredients;
    }

    private Recipes createRecipes(String title, Set<Ingredients> ingredientsSet) {
        Recipes recipes = new Recipes();
        recipes.setTitle(title);
        recipes.setIngredients(ingredientsSet);
        return recipes;
    }

}
