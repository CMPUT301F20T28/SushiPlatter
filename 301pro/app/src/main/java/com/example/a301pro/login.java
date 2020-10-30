package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private User login_user;
    protected FirebaseAuth mAuth;
   


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Get the Intent that started this activity and extract the string
        //隐藏title
        mAuth = FirebaseAuth.getInstance();


        AppCompatAcitiviy:getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        Button log = findViewById(R.id.btn_login);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                log_in();
//                finish();
            }
        });

        TextView reg = findViewById(R.id.textview_reg);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),register.class);
                startActivity(intent);
            }
        });
        }
    public void log_in() {
        EditText emailView = findViewById(R.id.text_username);
        EditText passwordView = findViewById(R.id.text_password);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(login.this, "Please ensure you have " +
                    "filled out all the fields.", Toast.LENGTH_SHORT).show();
        }
        else {
            validate(email, password);
        }
    }

    public void validate(String driverEmail, String driverPassword) {
        mAuth.signInWithEmailAndPassword(driverEmail, driverPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(login.this,
                                    "Login Successful.", Toast.LENGTH_SHORT).show();

                        } else {
                            //Log.w(TAG, "signInWithEmail: failure", task.getException());
                            Toast.makeText(login.this,
                                    "Login failed. Please check your info and try again.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
