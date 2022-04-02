package com.example.recipecorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    TextView loginText; Button signUpButton;
    EditText userNameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // getSupportActionBar().hide();
        userNameEditText = findViewById(R.id.signUpUsername);
        passwordEditText = findViewById(R.id.signUpPassword);
        signUpButton = findViewById(R.id.signUpButton);
        loginText = findViewById(R.id.loginText);

        signUpButton.setOnClickListener(signUpListener);

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(SignUp.this, Login.class);
                startActivity(in);
                finish();
            }
        });
    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String username = userNameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.equals(""))
            {
                userNameEditText.setError("Please enter a username");
            }
            else if (password.equals(""))
            {
                passwordEditText.setError("Please enter a password");
            }
            else
            {
                DatabaseHelper dbHelper = new DatabaseHelper(SignUp.this);
                long userid = dbHelper.signUp(username, password);
                Log.d("User ID", String.valueOf(userid));

                if (userid == -1)
                {
                    Toast.makeText(SignUp.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignUp.this, "Welcome to Recipe Corner!", Toast.LENGTH_SHORT).show();
                    // Add user to SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putLong("userid", userid);
                    editor.apply();

                    // Intent to Home
                    Intent in = new Intent(SignUp.this, Home.class);
                    startActivity(in);
                    finish();
                }

            }

        }
    };
}