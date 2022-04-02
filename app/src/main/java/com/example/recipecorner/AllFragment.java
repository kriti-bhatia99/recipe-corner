package com.example.recipecorner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    AllAdapter adapter;
    DatabaseHelper dbHelper;
    ArrayList<RecipeData> allRecipes;
    SharedPreferences preferences;
    long userid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        recyclerView = view.findViewById(R.id.allRecyclerView);

        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = preferences.getLong("userid", -1);

        dbHelper = new DatabaseHelper(getContext());
        allRecipes = dbHelper.getAllRecipes(userid);

        adapter = new AllAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        allRecipes = dbHelper.getAllRecipes(userid);
        adapter.notifyDataSetChanged();
    }

    class AllAdapter extends RecyclerView.Adapter<AllAdapter.AllViewHolder>
    {
        public class AllViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView, favImageView;
            TextView recipeTextView; CardView cardView;

            public AllViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.customImageView);
                cardView = itemView.findViewById(R.id.customCardView);
                favImageView = itemView.findViewById(R.id.customFavView);
                recipeTextView = itemView.findViewById(R.id.customTextView);
            }
        }

        @NonNull
        @Override
        public AllAdapter.AllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_recipe, parent, false);
            return new AllAdapter.AllViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AllAdapter.AllViewHolder holder, int position)
        {
            if (allRecipes.size() > 0)
            {
                RecipeData recipe = allRecipes.get(position);
                String text = recipe.getrName();
                String instructions = recipe.getrInstructions();
                String ingredients = recipe.getrIngredients();
                boolean favourite = recipe.isrFavourite();
                int p = holder.getAdapterPosition();
                int icon;

                if (p%2 == 0)
                {
                    icon = R.drawable.icon_1;
                }
                else
                {
                    icon = R.drawable.icon_2;
                }

                holder.recipeTextView.setText(text);
                holder.imageView.setImageResource(icon);

                if (favourite)
                {
                    holder.favImageView.setImageResource(R.drawable.full_heart);
                }
                else
                {
                    holder.favImageView.setImageResource(R.drawable.empty_heart);
                }

                holder.favImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (favourite)
                        {
                            // Remove from favourite
                            long result = dbHelper.removeFromFavourites(userid, text);

                            if (result == -1)
                            {
                                Toast.makeText(getContext(), "Error adding to favourites", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                recipe.setrFavourite(false);
                                holder.favImageView.setImageResource(R.drawable.empty_heart);
                                Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else
                        {
                            // Add to favourite
                            long result = dbHelper.addToFavourites(userid, text);

                            if (result == -1)
                            {
                                Toast.makeText(getContext(), "Error adding to favourites", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                recipe.setrFavourite(true);
                                holder.favImageView.setImageResource(R.drawable.full_heart);
                                Toast.makeText(getContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

                // View full recipe
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(getContext(), FullRecipe.class);
                        in.putExtra("name", text);
                        in.putExtra("ingredients", ingredients);
                        in.putExtra("instructions", instructions);
                        startActivity(in);
                    }
                });
            }
            else
            {
                Toast.makeText(getContext(), "No recipes found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public int getItemCount() {
            return allRecipes.size();
        }
    }
}