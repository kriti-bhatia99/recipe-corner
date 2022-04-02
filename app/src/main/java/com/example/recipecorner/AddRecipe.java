package com.example.recipecorner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddRecipe extends AppCompatActivity {

    Button addRecipeButton;
    EditText recipeNameEditText, recipeIngredientEditText, recipeInstructionsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        addRecipeButton = findViewById(R.id.addRecipeButton);
        recipeNameEditText = findViewById(R.id.addRecipeName);
        recipeIngredientEditText = findViewById(R.id.addRecipeIngredients);
        recipeInstructionsEditText = findViewById(R.id.addRecipeInstructions);

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rName = recipeNameEditText.getText().toString();
                String rIngredients = recipeIngredientEditText.getText().toString();
                String rInstructions = recipeInstructionsEditText.getText().toString();

                if (TextUtils.isEmpty(rName))
                {
                    recipeNameEditText.setError("Please enter a name for the recipe");
                }
                else
                {
                    DatabaseHelper dbHelper = new DatabaseHelper(AddRecipe.this);
                    long result = dbHelper.addRecipe(rName, rIngredients, rInstructions);

                    if (result == -1)
                    {
                        Toast.makeText(AddRecipe.this, "Recipe already exists", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AddRecipe.this, "Recipe added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        });
    }
}