package com.example.tvtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    public void setUsername (String usernameString) {
        this.usernameString=usernameString;
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
    }

    public void userLogin() {
        if (usernameString.equals("1") && passwordString.equals("1")){
            Toast.makeText(Login.this, "Correct login details", Toast.LENGTH_SHORT).show();
            String title = getApplicationContext().toString();
            Toast.makeText(getApplicationContext(), title, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Favorites.class);
                startActivity(intent);
        }
        else if (usernameString.equals("") || passwordString.equals("")){
            Toast.makeText(Login.this, "Enter username & password", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(Login.this, "Incorrect login details", Toast.LENGTH_SHORT).show();
        }
    }
}