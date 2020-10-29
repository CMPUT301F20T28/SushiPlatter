package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class register extends AppCompatActivity {
    private User newUser;
    private FirebaseAuth mAuth;
    public String TAG = "Register";
    Button registerButton;

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
        registerButton = findViewById(R.id.btn_register);

        String firstName = userFirstName.getText().toString();
        String lastName = userLastName.getText().toString();
        String phoneNumber = userPhone.getText().toString();
        String email = userEmail.getText().toString();
        String userName = userUserName.getText().toString();
        String password = userPassword.getText().toString();

        newUser = new User(userName,email,password,firstName,lastName,phoneNumber);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }
        });

    }
}