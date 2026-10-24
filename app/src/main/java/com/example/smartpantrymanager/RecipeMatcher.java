package com.example.smartpantrymanager;

import java.util.ArrayList;
import java.util.List;

public class RecipeMatcher {

    public static List<Recipe> getExactMatches(List<Recipe> allRecipes, List<String> userPantry) {
        List<Recipe> matchedRecipes = new ArrayList<>();

        // Convert pantry items to lowercase for accurate matching
        List<String> lowerCasePantry = new ArrayList<>();
        for (String item : userPantry) {
            lowerCasePantry.add(item.trim().toLowerCase());
        }

        for (Recipe recipe : allRecipes) {
            boolean hasAllIngredients = true;

            for (String required : recipe.getRequiredIngredients()) {
                if (!lowerCasePantry.contains(required.toLowerCase())) {
                    hasAllIngredients = false;
                    break; // Missing an ingredient, strict match fails
                }
            }

            if (hasAllIngredients) {
                matchedRecipes.add(recipe);
            }
        }
        return matchedRecipes;
    }
}