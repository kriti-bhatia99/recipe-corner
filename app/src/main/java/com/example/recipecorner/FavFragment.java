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

public class FavFragment extends Fragment {

    RecyclerView recyclerView;
    FavAdapter adapter;
    ArrayList<RecipeData> favRecipes;
    DatabaseHelper dbHelper;
    SharedPreferences preferences;
    long userid;
    TextView noFavsTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        recyclerView = view.findViewById(R.id.favRecyclerView);
        noFavsTextView = view.findViewById(R.id.noFavsTextView);

        preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        userid = preferences.getLong("userid", -1);

        dbHelper = new DatabaseHelper(getContext());
        favRecipes = dbHelper.getFavouriteRecipes(userid);

        adapter = new FavAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder>
    {

        public class FavViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView, favImageView;
            TextView recipeTextView; CardView cardView;

            public FavViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.customImageView);
                favImageView = itemView.findViewById(R.id.customFavView);
                recipeTextView = itemView.findViewById(R.id.customTextView);
                cardView = itemView.findViewById(R.id.customCardView);
            }
        }

        @NonNull
        @Override
        public FavAdapter.FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_recipe, parent, false);
            return new FavAdapter.FavViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavAdapter.FavViewHolder holder, int position) {
            if (favRecipes.size() > 0)
            {
                noFavsTextView.setVisibility(View.INVISIBLE);
                RecipeData recipe = favRecipes.get(position);
                String text = recipe.getrName();
                String instructions = recipe.getrInstructions();
                String ingredients = recipe.getrIngredients();
                int p = holder.getAdapterPosition();
                int icon;

                if (p % 2 == 0)
                {
                    icon = R.drawable.icon_1;
                }
                else
                    {
                    icon = R.drawable.icon_2;
                }

                holder.recipeTextView.setText(text);
                holder.imageView.setImageResource(icon);
                holder.favImageView.setImageResource(R.drawable.full_heart);

                holder.favImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Remove from favourites
                        dbHelper.removeFromFavourites(userid, text);
                        favRecipes = dbHelper.getFavouriteRecipes(userid);
                        if (favRecipes.size() == 0)
                        {
                            noFavsTextView.setVisibility(View.VISIBLE);
                        }

                        Toast.makeText(getContext(), "Removed from favourites", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });

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
                noFavsTextView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return favRecipes.size();
        }
    }
}