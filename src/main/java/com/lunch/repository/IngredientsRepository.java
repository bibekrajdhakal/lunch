package com.lunch.repository;

import com.lunch.model.Ingredients;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class IngredientsRepository {

    private final EntityManager manager;

    @Inject
    public IngredientsRepository(EntityManager manager) {
        this.manager = manager;
    }

    public void save(Ingredients ingredients) {
        manager.getTransaction().begin();
        manager.persist(ingredients);
        manager.getTransaction().commit();
    }

}
