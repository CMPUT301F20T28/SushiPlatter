package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    private User login_user;
    protected FirebaseAuth mAuth;
    Switch mSwitch;
    EditText passwordView;

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

        mSwitch = (Switch) findViewById(R.id.show_password);
        passwordView = findViewById(R.id.text_password);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{

                    passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log_in();
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
        final EditText usernameView = findViewById(R.id.text_username);
        final EditText passwordView = findViewById(R.id.text_password);

        String username = usernameView.getText().toString();
        final String password = passwordView.getText().toString();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(login.this, "Please ensure you have " +
                    "filled out all the fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            CollectionReference collectionReference = db.collection("userDict");
            DocumentReference docRef = collectionReference.document(username);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String email = (String) document.getData().get("email");

                            validate(email, password, usernameView, passwordView);

                        } else {
                            Toast.makeText(login.this,
                                    "Login failed. Please check your Username and try again.",
                                    Toast.LENGTH_SHORT).show();
                            usernameView.setText("");
                            passwordView.setText("");

                        }
                    } else {
                        Toast.makeText(login.this,
                                "Login failed. Please check your info and try again.",
                                Toast.LENGTH_SHORT).show();
                        usernameView.setText("");
                        passwordView.setText("");

                    }
                }
            });
        }
    }


    public void validate(String Email, String Password, final EditText usernameView, final EditText passwordView) {
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(login.this,
                                    "Login Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getBaseContext(),MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(login.this,
                                    "Login failed. Please check your info and try again.",
                                    Toast.LENGTH_SHORT).show();
                            usernameView.setText("");
                            passwordView.setText("");
                        }
                    }
                });
    }


}
