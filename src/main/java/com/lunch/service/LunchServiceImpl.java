package com.lunch.service;

import com.lunch.dto.RecipesList;
import com.lunch.dto.RecipesResource;
import com.lunch.model.Recipes;
import com.lunch.repository.RecipesRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LunchServiceImpl implements LunchService {

    private final RecipesRepository repository;

    @Inject
    public LunchServiceImpl(RecipesRepository repository) {
        this.repository = repository;
    }

    @Override
    public RecipesList get() {
        List<Recipes> recipes = repository.findAll();

        List<Recipes> recipesWithIngredientsWithInUseByDate = recipes.stream()
                .filter(this::isWithInUseByDate)
                .sorted(Comparator.comparing(this::isWithInBestBeforeDate).reversed())
                .collect(Collectors.toList());

        RecipesList list = new RecipesList();
        list.setRecipesResources(recipesWithIngredientsWithInUseByDate.stream()
                .map(RecipesResource::from)
                .collect(Collectors.toList()));

        return list;
    }

    /**
     * checks if all the ingredients used in a recipe are with in the use by date
     * @param recipes the recipes whose ingredients date should be inspected
     * @return true if all ingredients are within the use by date
     */
    boolean isWithInUseByDate(Recipes recipes) {
        return recipes.getIngredients()
                .stream()
                .allMatch(ingredients -> isFirstDateBeforeSecondDate(LocalDate.now(), ingredients.getUseBy()));
    }

    /**
     * checks if all the ingredients used in a recipe are with in the best before date
     * @param recipes the recipes whose ingredients date should be inspected
     * @return true if all ingredients are within the best before date
     */
    boolean isWithInBestBeforeDate(Recipes recipes) {
        return recipes.getIngredients()
                .stream()
                .allMatch(ingredients -> isFirstDateBeforeSecondDate(LocalDate.now(), ingredients.getBestBefore()));
    }

    /**
     * @param firstDate LocalDate to compare
     * @param secondDate Date to be compared
     * @return true if first date is before the second date
     */
    boolean isFirstDateBeforeSecondDate(LocalDate firstDate, Date secondDate) {
        return firstDate.isBefore(secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

}
