package com.lunch.service;

import com.lunch.dto.RecipesList;
import com.lunch.dto.RecipesResource;
import com.lunch.model.Recipes;
import com.lunch.repository.RecipesRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
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

        RecipesList list = new RecipesList();
        list.setRecipesResources(recipes.stream().map(RecipesResource::from).collect(Collectors.toList()));

        return list;
    }

}
