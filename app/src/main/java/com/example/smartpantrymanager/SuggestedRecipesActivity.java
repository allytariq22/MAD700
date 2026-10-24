package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView recipeList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recipeList = findViewById(R.id.recipeList);
        dbHelper = new DatabaseHelper(this);

        // 1. Define Sample Recipes
        List<Recipe> allRecipes = new ArrayList<>();
        allRecipes.add(new Recipe("Tomato Soup", Arrays.asList("Tomato", "Onion"), "Boil and blend."));
        allRecipes.add(new Recipe("Omelette", Arrays.asList("Egg", "Cheese"), "Whisk eggs, cook, add cheese."));
        allRecipes.add(new Recipe("Fruit Salad", Arrays.asList("Apple", "Banana"), "Chop and mix."));

        // 2. Get User Pantry from Database
        List<String> userPantry = dbHelper.getAllIngredients();
        if (userPantry == null) userPantry = new ArrayList<>();

        // 3. Run Strict-Matching Algorithm
        List<Recipe> matchedRecipes = RecipeMatcher.getExactMatches(allRecipes, userPantry);

        // 4. Display Results
        if (matchedRecipes.isEmpty()) {
            Toast.makeText(this, "No recipes match your current pantry.", Toast.LENGTH_LONG).show();
        } else {
            ArrayAdapter<Recipe> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, matchedRecipes);
            recipeList.setAdapter(adapter);
        }

        // 5. Click a recipe to see instructions (Captures your Recipe Detail requirement)
        recipeList.setOnItemClickListener((parent, view, position, id) -> {
            Recipe clickedRecipe = matchedRecipes.get(position);
            Toast.makeText(this, clickedRecipe.getInstructions(), Toast.LENGTH_LONG).show();
        });
    }
}