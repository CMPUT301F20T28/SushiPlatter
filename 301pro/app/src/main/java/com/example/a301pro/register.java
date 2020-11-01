package com.example.a301pro;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {
    private User newUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public String TAG = "Register";
    public boolean userExist = false;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button registerButton;
    Button login;
    Switch mSwitch;
    EditText passwordView;
    EditText passwordViewCheck;

    /**
     * User sign up
     * @param savedInstanceState
     *
     * reference: https://firebase.google.com/docs/auth/android/start
     *            https://www.youtube.com/watch?v=Z-RE1QuUWPg&ab_channel=CodeWithMazn
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        // Get the Intent that started this activity and extract the string
        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final EditText userFirstName = findViewById(R.id.first_name);
        final EditText userLastName = findViewById(R.id.last_name);
        final EditText userPhone = findViewById(R.id.phone_num);
        final EditText userEmail = findViewById(R.id.Email);
        final EditText userUserName = findViewById(R.id.text_username);
        final EditText userPassword = findViewById(R.id.text_password);
        final EditText userPasswordRepeat = findViewById(R.id.text_password_check);
        registerButton = findViewById(R.id.btn_register);
        login = findViewById(R.id.login_in_register);
        mAuth = FirebaseAuth.getInstance();
        mSwitch = (Switch) findViewById(R.id.show_password);
        passwordView = findViewById(R.id.text_password);
        passwordViewCheck = findViewById(R.id.text_password_check);

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    passwordView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordViewCheck.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{

                    passwordView.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordViewCheck.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getBaseContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                userExist = false;
                final String firstName = userFirstName.getText().toString();
                final String lastName = userLastName.getText().toString();
                final String phoneNumber = userPhone.getText().toString();
                final String email = userEmail.getText().toString();
                final String userName = userUserName.getText().toString();
                final String password = userPassword.getText().toString();
                final String passwordCheck = userPasswordRepeat.getText().toString();

                if (TextUtils.isEmpty(firstName)){
                    userFirstName.setError("First name is required!");
                    userExist = true;

                }

                if (TextUtils.isEmpty(lastName)){
                    userLastName.setError("Last name is required!");
                    userExist = true;
                }

                if (TextUtils.isEmpty(phoneNumber)){
                    userPhone.setError("Phone number is required!");
                    userExist = true;
                }

                if (TextUtils.isEmpty(userName)){
                    userUserName.setError("User name is required!");
                    userExist = true;
                }

                if (TextUtils.isEmpty(email)){
                    userEmail.setError("Email address is required!");
                    userExist = true;
                }

                if (TextUtils.isEmpty(password)){
                    userPassword.setError("Password is required!");
                    userExist = true;
                }

                if (TextUtils.isEmpty(passwordCheck)){
                    userPasswordRepeat.setError("Please repeat your password!");
                    userExist = true;
                }

                if (!password.equals(passwordCheck)){
                    Toast.makeText(register.this, "Passwords are not the same, please try again!", Toast.LENGTH_SHORT).show();
                    userPassword.setError("Passwords not match!");
                    userPasswordRepeat.setError("Passwords not match!");
                    userExist = true;

                }else{
                    if (password.length()<6){
                        userPassword.setError("Password length should be >= 6!");
                        userPasswordRepeat.setError("Password length should be >= 6!");
                        userExist = true;
                    }
                }

                if (userExist==true){
                    return;
                }

                db.collection("userDict").document(userName).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.getResult().exists()){
                                    userUserName.setError("Username already exists!");
                                    userExist = true;
                                    return;
                                }else{
                                    if(userExist == false) {
                                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "createUserWithEmail:success");
                                                    final FirebaseUser user = mAuth.getCurrentUser();
                                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(userName).build();
                                                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // Sign up success, go to login
                                                                final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                 Log.d(TAG, "createUserWithEmail:success");
                                                                 Toast.makeText(register.this, "User created!", Toast.LENGTH_SHORT).show();
                                                                 newUser = new User(userName, email, password, firstName, lastName, phoneNumber, UID);
                                                                createAccount(newUser);
                                                                Intent intent = new Intent(getBaseContext(), login.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                        // If sign up fails, display a message to the user.
                                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                        Toast.makeText(register.this, "Sign up failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }
                            }
                        });
            }
        });
    }




    /**
     * Send new user info to FireBase
     *
     * @param User newUser
     */
    private void createAccount(User newUser){
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", newUser.getEmail());
        userInfo.put("phoneNumber", newUser.getPhoneNumber());
        userInfo.put("firstName", newUser.getFirstName());
        userInfo.put("lastName", newUser.getLastName());
//        userInfo.put("password", newUser.getPassword());
        userInfo.put("userName", newUser.getUserName());
        userInfo.put("UID", newUser.getUID());

        db.collection("Users").document(newUser.getUID())
                .set(userInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "New user info successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing new user info", e);
                    }
                });

        Map<String, Object> userDict = new HashMap<>();
        userDict.put("email", newUser.getEmail());
        userDict.put("UID", newUser.getUID());
        db.collection("userDict").document(newUser.getUserName())
                .set(userDict)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "userDict successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing userDict", e);
                    }
                });
    }
}