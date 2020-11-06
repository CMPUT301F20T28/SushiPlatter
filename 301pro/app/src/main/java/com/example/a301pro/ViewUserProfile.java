package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows user to view the personal information as well as change the contact information
 */
public class ViewUserProfile extends AppCompatActivity {
    EditText phoneShow, userFirstNameShow, userLastNameShow, emailShow;
    Button edit, profileQuit;
    String Tag = "ViewUserProfile";
    String firstName, lastName, phoneNumber, email;
    private FirebaseAuth mAuth;

    /**
     * Provide functionality viewing and editing personal profile
     * @param savedInstanceState data of previous instance
     * @return layout of the fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_profile);
        AppCompatAcitiviy:getSupportActionBar().hide();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        userFirstNameShow = findViewById(R.id.user_first_name_display);
        userLastNameShow = findViewById(R.id.user_last_name_display);
        phoneShow = findViewById(R.id.phone_num_display);
        emailShow = findViewById(R.id.user_email_display);
        edit = findViewById(R.id.edit_profile);
        profileQuit = findViewById(R.id.profile_quit);

        DocumentReference docRef = db.collection("Users").document(getUserID());
        mAuth = FirebaseAuth.getInstance();
        phoneShow.setEnabled(false);
        userFirstNameShow.setEnabled(false);
        userLastNameShow.setEnabled(false);
        emailShow.setEnabled(false);


        // get user data
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        firstName = document.getString("firstName").toUpperCase();
                        Log.i(Tag,"First name"+firstName);

                        lastName = document.getString("lastName").toUpperCase();
                        Log.i(Tag,"Last name"+lastName);

                        email = document.getString("email").toUpperCase();
                        Log.i(Tag,"email"+document.getString("email"));

                        phoneNumber = document.getString("phoneNumber");
                        Log.i(Tag,"phoneNumber"+document.getString("phoneNumber"));

                        phoneShow.setText(phoneNumber);
                        emailShow.setText(email);
                        userFirstNameShow.setText(firstName);
                        userLastNameShow.setText(lastName);

                    } else {
                        Log.d(Tag, "No such document");
                    }
                } else {
                    Log.d(Tag, "get failed with ", task.getException());
                }
            }
        });

        // edit personal data
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit.getText().toString().equals("Confirm")){
                    edit.setText("Confirm");
                    phoneShow.setEnabled(true);
                    userFirstNameShow.setEnabled(true);
                    userLastNameShow.setEnabled(true);

                    // Uncomment the following line if checking if the edited email already exists is finished
                    emailShow.setEnabled(true);

                }else{
                    final boolean[] profileChangeSuccess = {true};
                    final String newPhoneNumber = phoneShow.getText().toString();
                    String newFirstName = userFirstNameShow.getText().toString();
                    String newLastName = userLastNameShow.getText().toString();
                    final String newEmail = emailShow.getText().toString();

                    if(!newPhoneNumber.equals(phoneNumber)){
                        if (newPhoneNumber.length()>0){
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("phoneNumber", newPhoneNumber);
                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .update(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ViewUserProfile.this,"Phone number successfully updated!", Toast.LENGTH_SHORT).show();
                                            Log.d("Change Phone#", "User phone number successfully changed!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            profileChangeSuccess[0] = false;
                                            phoneShow.setError("An error occurs, please try again!");
                                            Log.w("Change Phone#", "Error change new user phone number", e);
                                        }
                                    });
                        }else{
                            profileChangeSuccess[0] = false;
                            phoneShow.setError("Please enter a new phone number!");
                        }
                    }

                    if((!newFirstName.toLowerCase().equals(firstName.toLowerCase())) || (!newLastName.toLowerCase().equals(lastName.toLowerCase()))){
                        if ((newFirstName.length()>0) && (newLastName.length()>0)){
                            Map<String, Object> userName = new HashMap<>();
                            userName.put("firstName", newFirstName);
                            userName.put("lastName", newLastName);
                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .update(userName)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ViewUserProfile.this,"User's full name successfully updated!", Toast.LENGTH_SHORT).show();
                                            Log.d("Change full name#", "User's full name successfully changed!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            profileChangeSuccess[0] = false;
                                            Log.w("Change full name#", "Error change user's full name", e);
                                        }
                                    });
                        }else{
                            profileChangeSuccess[0] = false;
                            if ((newLastName.length() == 0)){
                                userLastNameShow.setError("Your last name cannot be empty!");

                            }
                            if((newFirstName.length() == 0)){
                                userFirstNameShow.setError("Your first name cannot be empty!");
                            }
                        }
                    }

                    if (!newEmail.toLowerCase().equals(email.toLowerCase())){
                        if (newEmail.length()>0){
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(newEmail.toLowerCase())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Map<String, Object> userEmail = new HashMap<>();

                                                userEmail.put("email", newEmail);
                                                db.collection("Users").document(mAuth.getCurrentUser().getUid()).update(userEmail);
                                                db.collection("userDict").document(mAuth.getCurrentUser().getDisplayName()).update(userEmail);
                                                Toast.makeText(ViewUserProfile.this,"User's email successfully updated!", Toast.LENGTH_SHORT).show();
                                                Log.d("Change email#", "User's email successfully changed!");
                                            }else{
                                                profileChangeSuccess[0] = false;
                                                emailShow.setError("Email existed, try another one!");
                                                Toast.makeText(ViewUserProfile.this,"Email existed, try another one!", Toast.LENGTH_SHORT).show();
                                                Log.w("Change email#", "Error change user's email");
                                            }
                                        }
                                    });

                        }else{
                            profileChangeSuccess[0] = false;
                            emailShow.setError("Your email address cannot be empty!");
                        }
                    }

                    if(profileChangeSuccess[0] == true){
                        edit.setText("EDIT PROFILE");
                        finish();
                    };
                }
            }
        });

        // cancel editing
        profileQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    /**
     * Get uid of the current logged in user
     * @return uid as a string
     */
    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}