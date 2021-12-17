package com.example.tvtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvtracker.DB.UserQuery;

public class Login extends MainActivity {
    private Button login;
    private EditText usernameInput;
    private EditText passwordInput;
    private String usernameString;
    private String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.button_loginScreen);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getCredentials();
                userLogin();
            }
        });
    }
    public String getUsername() {
        return usernameString;
    }

    public void setUsername (String usernameString) {this.usernameString=usernameString;
    }

    public String getPassword() {
        return passwordString;
    }

    public void setPassword(String passwordString) {
        this.passwordString = passwordString;
    }

    public void getCredentials() {
        usernameInput = (EditText) findViewById(R.id.usernameSignUp);
        passwordInput = (EditText) findViewById(R.id.passwordSignUp);
        setUsername(usernameInput.getText().toString());
        setPassword(passwordInput.getText().toString());

        //for nickname retrieval
        SharedPreferences nicknamePref = this.getSharedPreferences("com.example.tvtracker", Context.MODE_PRIVATE);
        nicknamePref.edit().putString("usernameInput", usernameString).apply();
    }

    public void userLogin() {

        String result = String.valueOf(UserQuery.userExists(usernameString, passwordString));

        int userId = UserQuery.userExists(usernameString, passwordString);
        if (userId == 0) {
            Toast.makeText(Login.this, "Incorrect login details", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPref = getSharedPreferences("userPref", Login.this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
            editor.putInt("userId", userId);
            editor.apply();

            Intent intent = new Intent(this, Favorites.class);
            intent.putExtra("userId", userId);
            intent.putExtra("usernameInput", usernameString);
            startActivity(intent);
        }
    }
}
