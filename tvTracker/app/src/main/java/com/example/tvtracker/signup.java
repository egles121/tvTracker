package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvtracker.DB.SqlConnection;
import com.example.tvtracker.DB.SqlQuery;
import com.example.tvtracker.DB.users;

import java.sql.Connection;
import java.sql.SQLException;

public class signup extends AppCompatActivity {
    private Button signup;

    EditText ET_username, ET_password, ET_email;
    String username, password, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ET_username = (EditText) findViewById(R.id.new_username);
        ET_password = (EditText) findViewById(R.id.new_password);
        ET_email = (EditText) findViewById(R.id.email);

        signup = (Button) findViewById(R.id.button_signupScreen);
        signup.setOnClickListener(v -> userSignUp());

    }

    public void userSignUp() {

            try {
                username = ET_username.getText().toString();
                password = ET_password.getText().toString();
                email = ET_email.getText().toString();

                if (users.userExists(username) == false) {
                    users.insertDetails(username, password, email);
                    Intent intent = new Intent (this,login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(signup.this, "This username already exists", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }


}
