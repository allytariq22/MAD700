package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pantry.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity REAL, unit TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, instructions TEXT)");
        db.execSQL("CREATE TABLE recipe_ingredients (recipe_id INTEGER, name TEXT, quantity REAL, unit TEXT)");
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pantry");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        db.execSQL("DROP TABLE IF EXISTS recipe_ingredients");
        onCreate(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        // Pre-load 15-20 recipes here as per requirements
        db.execSQL("INSERT INTO recipes (name, instructions) VALUES ('Omelette', 'Beat eggs, fry in pan.')");
        db.execSQL("INSERT INTO recipe_ingredients (recipe_id, name, quantity, unit) VALUES (1, 'egg', 2, 'whole')");
    }

    public List<String> getPantryItems() {
        List<String> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM pantry", null);
        if (cursor.moveToFirst()) {
            do {
                items.add(cursor.getString(0).toLowerCase());
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    public void deleteIngredient(String itemToRemove) {
    }

    public void addIngredient(String newItem) {
    }

    public ArrayList<String> getAllIngredients() {
        return null;
    }
}