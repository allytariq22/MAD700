package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PantryActivity extends AppCompatActivity {

    private EditText etIngredient;
    private ArrayList<String> ingredients;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        etIngredient = findViewById(R.id.etIngredient);
        Button btnAdd = findViewById(R.id.btnAdd);
        ListView ingredientList = findViewById(R.id.ingredientList);

        dbHelper = new DatabaseHelper(this);

        // Retrieve existing ingredients from SQLite database
        ingredients = dbHelper.getAllIngredients();
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredients);
        ingredientList.setAdapter(adapter);

        // Create Operation (Add)
        btnAdd.setOnClickListener(v -> {
            String newItem = etIngredient.getText().toString().trim();

            // Validation Error Logic
            if (newItem.isEmpty()) {
                Toast.makeText(this, "Ingredient cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.addIngredient(newItem);
            ingredients.add(newItem);
            adapter.notifyDataSetChanged();
            etIngredient.setText("");
        });

        // Delete Operation (Long click to remove)
        ingredientList.setOnItemLongClickListener((parent, view, position, id) -> {
            String itemToRemove = ingredients.get(position);
            dbHelper.deleteIngredient(itemToRemove);
            ingredients.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Deleted: " + itemToRemove, Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}