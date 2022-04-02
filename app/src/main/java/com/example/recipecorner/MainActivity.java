package com.example.recipecorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        Handler handler = new Handler();
        addInitialRecipeData();
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String username = sp.getString("username", "");

                if (username.equals(""))
                {
                    Intent in = new Intent(MainActivity.this, Login.class);
                    startActivity(in);
                    finish();
                }
                else
                {
                    Intent in = new Intent(MainActivity.this, Home.class);
                    startActivity(in);
                    finish();
                }

            }
        };

        handler.postDelayed(runnable, 3000);
    }

    void addInitialRecipeData()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ArrayList<RecipeData> recipes = dbHelper.getAllRecipes();

        if (recipes.size() == 0)
        {
            ArrayList<RecipeData> initialRecipes = new ArrayList<>();

            initialRecipes.add(new RecipeData("Chicken Roll",
                    "200 gm chopped into cubes chicken\n" +
                    "1 medium chopped tomato\n" +
                    "1/2 pinch red chilli powder\n" +
                    "2 tablespoon vegetable oil\n" +
                    "1/2 cut into strips cucumber\n" +
                    "1/2 tablespoon chopped coriander leaves\n" +
                    "1 large thinly sliced onion\n" +
                    "2 medium chopped green chilli\n" +
                    "2 pinches garam masala powder\n" +
                    "1 lemon wedges\n" +
                    "1 teaspoon tomato ketchup\n" +
                    "1 tablespoon green chilli sauce\n",
                    "Chicken Roll is a delectable North Indian recipe made using all purpose flour, " +
                            "stir-fried chicken, yoghurt and a variety of vegetables rolled into paranthas. On days you do not " +
                            "want to prepare an elaborate meal, this delectable dish can be a saviour. Rolls are quite popular " +
                            "across India and they are often a favourite evening snack. Egg Roll, Kathi Kebab Roll, Mutton Roll, " +
                            "Paneer Roll, Potato Roll and even Fish Roll are among its many variations. This easy roll recipe is " +
                            "made using chicken and has the unforgettable aroma of Indian spices. You can also utilize your leftover " +
                            "parathas and chapatis in making this recipe. If you do not like to use all-purpose flour or maida, " +
                            "you can make it with whole wheat flour too. In fact, it can be made even with leftover chicken. " +
                            "All you have to ensure is that you use the right amount of spices so that the flavour is not lost. " +
                            "Easy to pack and carry, you can also take it to office or prepare it for picnics and day trips. " +
                            "A must try roll recipe for all chicken lovers!"));

            initialRecipes.add(new RecipeData("Donut",
                    "1 c. whole milk\n" +
                    "1/4 c. plus 1 tsp. granulated sugar, divided \n" +
                    "1 packet (or 2 1/4 tsp.) active dry yeast\n" +
                    "4 c. all-purpose flour, plus more if needed\n" +
                    "1/2 tsp. kosher salt\n" +
                    "6 tbsp. melted butter\n" +
                    "2 large eggs\n" +
                    "1/2 tsp. pure vanilla extract\n" +
                    "Canola or vegetable oil, for frying\n",
                    "Grease a large bowl with cooking spray and set aside. In a small, microwave-safe bowl or glass measuring " +
                            "cup, add milk. Microwave until lukewarm, 40 seconds. Add a teaspoon of sugar and stir to dissolve, then " +
                            "sprinkle over yeast and let sit until frothy, about 8 minutes.\n" +
                            "Make glaze: In a large bowl, whisk together milk, powdered sugar, and vanilla until smooth. Set aside." +
                            "Line a large baking sheet with paper towels. In a large dutch oven over medium heat, heat 2'' oil to 350°. " +
                            "Cook doughnuts, in batches, until deeply golden on both sides, about 1 minute per side. Holes will " +
                            "cook even faster!\n" +
                            "Transfer doughnuts to paper towel-lined baking sheet to drain and cool slightly. Dip into glaze, then place " +
                            "onto a cooling rack (or eat immediately!)"));

            initialRecipes.add(new RecipeData("Dosa",
                    "3 cups rice\n" +
                    "1 cup urad daal (split, skinless black gram)\n" +
                    "3/4 teaspoon fenugreek seeds\n" +
                    "Salt (to taste)\n" +
                    "Vegetable / canola / sunflower cooking oil\n",
                    "Wash the rice and urad daal well. Add the fenugreek seeds to the mix and fill enough water in the " +
                            "rice-daal bowl to cover them about 2-inch deep. Soak overnight.\n" +
                            "Put some cooking oil in a small bowl and keep ready. You will also need a bowl of ice cold water, a large, " +
                            "flat nonstick pan, 2 sheets of paper towel, a ladle, a spatula, and a basting brush.\n" +
                            "When the upper surface begins to look cooked (it will no longer look soft or runny), flip the dosa. By this " +
                            "time, ideally, the surface that was underneath should be light golden in color. Cook for 1 minute after flipping.\n" +
                            "The dosa is almost done. Fold it in half and allow to cook for 30 seconds more."));

            initialRecipes.add(new RecipeData("Pancake",
                    "1 1/4 cups milk\n" +
                    "1 egg\n" +
                    "3 tablespoons butter melted\n" +
                    "1 1/2 cups all-purpose flour\n" +
                    "3 1/2 teaspoons baking powder\n" +
                    "1 teaspoon salt\n" +
                    "1 tablespoon white sugar\n",
                    "In a large bowl, sift together the flour, baking powder, salt and sugar. Make a well in the center and pour" +
                            " in the milk, egg and melted butter; mix until smooth." +
                            "Heat a lightly oiled griddle or frying pan over medium high heat. Pour or scoop the batter onto the griddle, " +
                            "using approximately 1/4 cup for each pancake. Brown on both sides and serve hot."));

            initialRecipes.add(new RecipeData("Pasta",
                    "1 tsp oil\n" +
                    "1 tsp butter\n" +
                    "2 clove garlic, finely chopped\n" +
                    "1 inch ginger, finely chopped\n" +
                    "½ onion, finely chopped\n" +
                    "1 cup tomato pulp\n" +
                    "¼ tsp turmeric / haldi\n" +
                    "½ tsp kashmiri red chilli powder\n" +
                    "2 tbsp tomato sauce\n" +
                    "½ tsp garam masala\n",
                    "Firstly, saute 1 inch ginger and 2 clove garlic in 1 tsp oil and 1 tsp butter." +
                            "further saute ½ onion till they turn soft." +
                            "additionally add 1 cup tomato pulp and saute well." +
                            "keep stirring till the tomato pulp thickens." +
                            "now add ¼ tsp turmeric, ½ tsp chilli powder, ½ tsp garam masala and ½ tsp salt." +
                            "saute till the spices gets cooked completely." +
                            "now add 2 tbsp corn, ¼ capsicum, ¼ carrot, 2 tbsp peas and 7 florets broccoli. saute for a minute." +
                            "add in 3 tbsp water and mix well." +
                            "cover and simmer for 5 minutes." +
                            "now add in 2 tbsp tomato sauce and ½ tsp mixed herbs. mix well." +
                            "add in cooked pasta and mix gently till the sauce gets coated well." +
                            "finally, serve masala pasta indian style hot topped with cheese if required."));


            for (RecipeData recipeData : initialRecipes)
            {
                long result = dbHelper.addRecipe(recipeData.getrName(), recipeData.getrIngredients(),
                        recipeData.getrInstructions());

                if (result == -1)
                {
                    Log.d("Initial recipes", "Error adding " +  recipeData.getrName());
                }
                else
                {
                    Log.d("Initial recipes", "Added all initial recipes successfully");
                }
            }

        }

    }
}