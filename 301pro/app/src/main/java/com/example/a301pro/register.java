package com.example.a301pro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class register extends AppCompatActivity {
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        // Get the Intent that started this activity and extract the string
        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();


        final EditText userFirstName = findViewById(R.id.first_name);
        final EditText userLastName = findViewById(R.id.last_name);
        final EditText userPhone = findViewById(R.id.phone_num);
        final EditText userEmail = findViewById(R.id.Email);
        final EditText userUserName = findViewById(R.id.text_username);
        final EditText userPassword = findViewById(R.id.text_password);

        String firstName = userFirstName.getText().toString();
        String lastName = userLastName.getText().toString();
        String phoneNumber = userPhone.getText().toString();
        String email = userEmail.getText().toString();
        String userName = userUserName.getText().toString();
        String password = userPassword.getText().toString();

        newUser = new User(userName,email,password,firstName,lastName,phoneNumber);

    }
}
