package com.lunch.repository;

import com.lunch.model.Recipes;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class RecipesRepository {

    private final EntityManager manager;

    @Inject
    public RecipesRepository(EntityManager manager) {
        this.manager = manager;
    }

    public List<Recipes> findAll() {
        Query query = manager.createQuery("FROM Recipes");
        return query.getResultList();
    }

}
