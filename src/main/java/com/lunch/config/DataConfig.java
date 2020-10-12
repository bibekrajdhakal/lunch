package com.lunch.config;

import com.lunch.model.Ingredients;
import com.lunch.model.Recipes;
import com.lunch.provider.DataProvider;
import com.lunch.provider.ProviderResponse;
import com.lunch.repository.IngredientsRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class DataConfig {

    private final DataProvider provider;
    private final IngredientsRepository repository;

    @Inject
    public DataConfig(DataProvider provider, IngredientsRepository repository) {
        this.provider = provider;
        this.repository = repository;

        loadData();
    }

    private void loadData() {
        Map<String, Ingredients> ingredientsMap = getIngredients();
        Map<String, Set<String>> recipesMap = getRecipes();
        persistData(ingredientsMap, recipesMap);
    }

    private Map<String, Ingredients> getIngredients() {
        Map<String, Ingredients> ingredientsMap = new HashMap<>();
        ProviderResponse response = provider.getIngredients();
        JSONObject jsonObject = new JSONObject(response.getMessage());
        JSONArray jsonArray = jsonObject.getJSONArray("ingredients");
        for (int i = 0; i < jsonArray.length(); i++) {
            Ingredients ingredients = new Ingredients();
            ingredients.setTitle(jsonArray.getJSONObject(i).getString("title"));
            ingredients.setBestBefore(convertStringToDate(jsonArray.getJSONObject(i).getString("best-before")));
            ingredients.setUseBy(convertStringToDate(jsonArray.getJSONObject(i).getString("use-by")));
            ingredientsMap.put(ingredients.getTitle(), ingredients);
        }
        return ingredientsMap;
    }

    private Date convertStringToDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private Map<String, Set<String>> getRecipes() {
        ProviderResponse response = provider.getRecipes();
        JSONObject jsonObject = new JSONObject(response.getMessage());
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        Map<String, Set<String>> recipeIngredients = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Recipes recipes = new Recipes();
            recipes.setTitle(jsonArray.getJSONObject(i).getString("title"));
            JSONArray ingredientArray = jsonArray.getJSONObject(i).getJSONArray("ingredients");
            Set<String> list = new HashSet<>();
            for (int j = 0; j < ingredientArray.length(); j++) {
                list.add(ingredientArray.getString(j));
            }
            recipeIngredients.put(recipes.getTitle(), list);
        }
        return recipeIngredients;
    }

    private void persistData(Map<String, Ingredients> ingredientsMap, Map<String, Set<String>> recipesMap) {
        for (Map.Entry<String, Set<String>> entry : recipesMap.entrySet()) {
            Recipes recipes = new Recipes();
            recipes.setTitle(entry.getKey());
            for (String ingredient : entry.getValue()) {
                if (ingredientsMap.containsKey(ingredient)) {
                    Ingredients ingredients = ingredientsMap.get(ingredient);
                    ingredients.addRecipes(recipes);
                }
            }
        }
        ingredientsMap.values().forEach(repository::save);
    }

}
