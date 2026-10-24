package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {
    private String name;
    private List<String> requiredIngredients;
    private String instructions;

    public Recipe(String name, List<String> requiredIngredients, String instructions) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
        this.instructions = instructions;
    }

    public String getName() { return name; }
    public List<String> getRequiredIngredients() { return requiredIngredients; }
    public String getInstructions() { return instructions; }

    @Override
    public String toString() {
        return name; // This ensures the ListView displays the recipe name
    }
}