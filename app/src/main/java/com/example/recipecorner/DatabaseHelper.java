package com.example.recipecorner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "data";
    public static final String USER_TABLE = "users";
    public static final String FAV_TABLE = "favourites";
    public static final String RECIPE_TABLE = "recipes";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + USER_TABLE + " (userid integer PRIMARY KEY AUTOINCREMENT, username text UNIQUE, password text)");
        db.execSQL("create table " + FAV_TABLE + "(userid integer, recipe text)");
        db.execSQL("create table " + RECIPE_TABLE + "(recipe text PRIMARY KEY, ingredients text, instructions text)");
    }

    int login(String username, String password)
    {
        try
        {
            SQLiteDatabase sq = this.getReadableDatabase();
            Cursor cursor = sq.rawQuery("select * from " + USER_TABLE + " where username='" + username + "' and password='" + password + "'", null);
            int userid = 0;

            if (cursor.moveToNext())
            {
                userid = cursor.getInt(0);
            }

            return userid;
        }
        catch (Exception e)
        {
            return -1;
        }
    }

    long signUp(String username, String password)
    {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long result = sq.insert(USER_TABLE, null, cv);
        // Will return -1 in case of error
        //Call login function to get userid
        if (result != -1)
        {
            return login(username, password);
        }
        return result;
    }

    ArrayList<RecipeData> getAllRecipes(long userid)
    {
        SQLiteDatabase sq = this.getReadableDatabase();
        Cursor favCursor = sq.rawQuery("select * from " + FAV_TABLE + " where userid=" + userid, null);
        Cursor allCursor = sq.rawQuery("select * from " + RECIPE_TABLE, null);

        ArrayList<String> favRecipes = new ArrayList<String>();
        ArrayList<RecipeData> allRecipes = new ArrayList<RecipeData>();

        while (favCursor.moveToNext())
        {
            favRecipes.add(favCursor.getString(1));
        }

        while (allCursor.moveToNext())
        {
            RecipeData recipe = new RecipeData();

            recipe.setrName(allCursor.getString(0));
            recipe.setrIngredients(allCursor.getString(1));
            recipe.setrInstructions(allCursor.getString(2));

            if (favRecipes.contains(allCursor.getString(0)))
            {
                recipe.setrFavourite(true);
            }

            allRecipes.add(recipe);

        }

        return allRecipes;

    }

    ArrayList<RecipeData> getFavouriteRecipes(long userid)
    {
        SQLiteDatabase sq = this.getReadableDatabase();
        Cursor favCursor = sq.rawQuery("select * from " + FAV_TABLE + " where userid=" + userid, null);
        Cursor allCursor = sq.rawQuery("select * from " + RECIPE_TABLE, null);

        ArrayList<String> favRecipes = new ArrayList<String>();
        ArrayList<RecipeData> allRecipes = new ArrayList<RecipeData>();

        while (favCursor.moveToNext())
        {
            favRecipes.add(favCursor.getString(1));
        }

        while (allCursor.moveToNext())
        {
            if (favRecipes.contains(allCursor.getString(0)))
            {
                RecipeData recipe = new RecipeData();

                recipe.setrName(allCursor.getString(0));
                recipe.setrIngredients(allCursor.getString(1));
                recipe.setrInstructions(allCursor.getString(2));

                recipe.setrFavourite(true);
                allRecipes.add(recipe);
            }
        }

        return allRecipes;

    }

    ArrayList<RecipeData> getAllRecipes()
    {
        SQLiteDatabase sq = this.getReadableDatabase();
        Cursor allCursor = sq.rawQuery("select * from " + RECIPE_TABLE, null);

        ArrayList<RecipeData> allRecipes = new ArrayList<RecipeData>();

        while (allCursor.moveToNext())
        {
            RecipeData recipe = new RecipeData();

            recipe.setrName(allCursor.getString(0));
            recipe.setrIngredients(allCursor.getString(1));
            recipe.setrInstructions(allCursor.getString(2));

            allRecipes.add(recipe);

        }

        return allRecipes;

    }

    long addRecipe(String rName, String rIngredients, String rInstructions)
    {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("recipe", rName);
        cv.put("ingredients", rIngredients);
        cv.put("instructions", rInstructions);
        long result = sq.insert(RECIPE_TABLE, null, cv);
        return result;
    }

    long addToFavourites(long userid, String recipe)
    {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userid", userid);
        cv.put("recipe", recipe);
        long result = sq.insertOrThrow(FAV_TABLE, null, cv);
        return result;
    }

    long removeFromFavourites(long userid, String recipe)
    {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("userid", userid);
        cv.put("recipe", recipe);
        long result = sq.delete(FAV_TABLE, "userid = " + userid + " and recipe ='" + recipe + "'", null);
        return result;
    }

    long[] deleteRecipe(String recipe)
    {
        SQLiteDatabase sq = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("recipe", recipe);
        long r1 = sq.delete(FAV_TABLE, "recipe = '" + recipe + "'", null);
        long r2 = sq.delete(RECIPE_TABLE, "recipe = '" + recipe + "'", null);

        return new long[]{r1, r2};
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
