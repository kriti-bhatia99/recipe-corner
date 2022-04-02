package com.example.recipecorner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Home extends AppCompatActivity {

    AllFragment allFragment = new AllFragment();
    FavFragment favFragment = new FavFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNav; FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNav = findViewById(R.id.bottomNav);
        fab = findViewById(R.id.addRecipeButton);
        bottomNav.setOnNavigationItemSelectedListener(bottomNavListener);
        fragmentManager.beginTransaction().replace(R.id.frameLayout, allFragment).commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Home.this, AddRecipe.class);
                startActivity(in);
            }
        });

    }

    BottomNavigationView.OnNavigationItemSelectedListener bottomNavListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.miHome)
            {
                fragmentManager.beginTransaction().replace(R.id.frameLayout, allFragment).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.frameLayout, favFragment).commit();
            }

            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.miLogOut)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Log out");
            builder.setMessage("Are you sure you wish to log out?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear().apply();
                    Intent in = new Intent(Home.this, Login.class);
                    startActivity(in);
                    finish();
                }
            });

            builder.setNegativeButton("Cancel", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }

        return true;
    }
}