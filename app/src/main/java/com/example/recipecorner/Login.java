package com.example.recipecorner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton; TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getSupportActionBar().hide();
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        signUpText = findViewById(R.id.signUpText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(loginListener);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, SignUp.class);
                startActivity(in);
                finish();
            }
        });
    }

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            String username = loginUsername.getText().toString();
            String password = loginPassword.getText().toString();

            if (TextUtils.isEmpty(username))
            {
                loginUsername.setError("Enter a username");
            }
            else if (TextUtils.isEmpty(password))
            {
                loginPassword.setError("Enter a password");
            }
            else
            {
                DatabaseHelper dbHelper = new DatabaseHelper(Login.this);
                int userid = dbHelper.login(username, password);
                Log.d("User ID", String.valueOf(userid));

                if (userid == 0)
                {
                    Toast.makeText(Login.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Login.this, "Welcome back!", Toast.LENGTH_SHORT).show();
                    // Add user to SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.putLong("userid", userid);
                    editor.apply();

                    // Intent to Home
                    Intent in = new Intent(Login.this, Home.class);
                    startActivity(in);
                    finish();
                }
            }
        }
    };
}