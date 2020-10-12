package com.lunch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Ingredients {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String title;

    private Date bestBefore;

    private Date useBy;

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Recipes> recipes = new HashSet<>();

    public void addRecipes(Recipes recipe) {
        recipes.add(recipe);
        recipe.getIngredients().add(this);
    }

}
