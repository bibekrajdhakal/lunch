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

    private boolean isWithInUseByDate(Recipes recipes) {
        return recipes.getIngredients()
                .stream()
                .allMatch(ingredients -> isFirstDateBeforeSecondDate(LocalDate.now(), ingredients.getUseBy()));
    }

    private boolean isWithInBestBeforeDate(Recipes recipes) {
        return recipes.getIngredients()
                .stream()
                .allMatch(ingredients -> isFirstDateBeforeSecondDate(LocalDate.now(), ingredients.getBestBefore()));
    }

    private boolean isFirstDateBeforeSecondDate(LocalDate firstDate, Date secondDate) {
        return firstDate.isBefore(secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }

}
