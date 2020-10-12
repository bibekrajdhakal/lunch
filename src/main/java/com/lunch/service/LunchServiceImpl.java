package com.lunch.service;

import com.lunch.model.Recipes;
import com.lunch.repository.RecipesRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class LunchServiceImpl implements LunchService {

    private final RecipesRepository repository;

    @Inject
    public LunchServiceImpl(RecipesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Recipes> get() {
        return repository.findAll();
    }

}
