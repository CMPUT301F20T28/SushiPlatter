package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

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
        final EditText userPasswordRepeat = findViewById(R.id.text_password_check);

        registerButton = findViewById(R.id.btn_register);
        mAuth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final String firstName = userFirstName.getText().toString();
                final String lastName = userLastName.getText().toString();
                final String phoneNumber = userPhone.getText().toString();
                final String email = userEmail.getText().toString();
                final String userName = userUserName.getText().toString();
                final String password = userPassword.getText().toString();
                final String passwordCheck = userPasswordRepeat.getText().toString();



                if (TextUtils.isEmpty(firstName)){
                    userFirstName.setError("First name is required!");
                    return;
                }

                if (TextUtils.isEmpty(lastName)){
                    userLastName.setError("Last name is required!");
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)){
                    userPhone.setError("Phone number is required!");
                    return;
                }

                if (TextUtils.isEmpty(userName)){
                    userUserName.setError("User name is required!");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    userEmail.setError("Email address is required!");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    userPassword.setError("Password is required!");
                    return;
                }

                if (TextUtils.isEmpty(passwordCheck)){
                    userPasswordRepeat.setError("Please repeat your password!");
                    return;
                }

                if (!password.equals(passwordCheck)){
                    Toast.makeText(register.this, "Passwords are not the same, please try again!", Toast.LENGTH_SHORT).show();
                }




                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(register.this, "User created!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                            newUser = new User(userName,email,password,firstName,lastName,phoneNumber);

                        } else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(register.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    }
                });

            }
        });

    }
}