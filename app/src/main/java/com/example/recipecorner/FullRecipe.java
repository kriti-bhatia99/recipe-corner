package com.example.recipecorner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kc.unsplash.Unsplash;
import com.kc.unsplash.models.Photo;
import com.kc.unsplash.models.SearchResults;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class FullRecipe extends AppCompatActivity {

    ImageView recipeHeader, deleteRecipeImage;
    String rName, rInstructions, rIngredients; int rImage;
    TextView rNameTextView, rInstructionsTextView, rIngredientsTextView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_recipe);

        HashMap <String, Integer> recipeHeaders = new HashMap<String, Integer>();
        recipeHeaders.put("Chicken Roll", R.drawable.chicken_roll);
        recipeHeaders.put("Donut", R.drawable.donut);
        recipeHeaders.put("Dosa", R.drawable.dosa);
        recipeHeaders.put("Pancake", R.drawable.pancake);
        recipeHeaders.put("Pasta", R.drawable.pasta);

        Intent in = getIntent();
        rName = in.getStringExtra("name");
        rIngredients = in.getStringExtra("ingredients");
        rInstructions = in.getStringExtra("instructions");

        recipeHeader = findViewById(R.id.recipeHeaderImageView);
        deleteRecipeImage = findViewById(R.id.deleteRecipeImage);
        rNameTextView = findViewById(R.id.rNameTextView);
        rIngredientsTextView = findViewById(R.id.rIngredientsTextView);
        rInstructionsTextView = findViewById(R.id.rInstructionsTextView);

        try
        {
            rImage = recipeHeaders.get(rName);
            deleteRecipeImage.setVisibility(View.GONE);
        }
        catch (NullPointerException e)
        {
            rImage = R.drawable.recipe_header;
            deleteRecipeImage.setVisibility(View.VISIBLE);
        }

        rNameTextView.setText(rName);
        rIngredientsTextView.setText(rIngredients);
        rInstructionsTextView.setText(rInstructions);
        recipeHeader.setImageResource(rImage);
        dbHelper = new DatabaseHelper(this);

        deleteRecipeImage.setOnClickListener(deleteRecipeListener);

    }

    View.OnClickListener deleteRecipeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(FullRecipe.this);
            builder.setTitle("Delete " +  rName);
            builder.setMessage("Are you sure you want to delete this recipe?");
            builder.setNegativeButton("Cancel", null);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    long[] result = dbHelper.deleteRecipe(rName);

                    if (result[0] == -1 || result[1] == -1)
                    {
                        Toast.makeText(FullRecipe.this, "Error deleting", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(FullRecipe.this, "Deleted " + rName, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };

}