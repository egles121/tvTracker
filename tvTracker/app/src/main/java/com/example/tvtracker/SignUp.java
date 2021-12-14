package com.example.tvtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvtracker.DB.Users;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
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

                if (Users.userExists(username) == false && emailIsValid(email) == true && isEmpty(username, password, email) == false) {
                    Users.insertDetails(username, password, email);
                    Intent intent = new Intent (this, Login.class);
                    startActivity(intent);
                } else if (emailIsValid(email) == false) {
                    Toast.makeText(SignUp.this, "Invalid email address format", Toast.LENGTH_SHORT).show();
                }  else if (Users.userExists(username) == true) {
                    Toast.makeText(SignUp.this, "This username already exists", Toast.LENGTH_SHORT).show();
                } else if (isEmpty(username, password, email) == true) {
                    Toast.makeText(SignUp.this, "Required field is missing", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Sign up error", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    public static boolean emailIsValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isEmpty (String username, String password, String email) {
        if (username.length() > 0 && password.length() > 0 && email.length() >0) {
            return false;
        } else {
            return true;
        }
    }

}
