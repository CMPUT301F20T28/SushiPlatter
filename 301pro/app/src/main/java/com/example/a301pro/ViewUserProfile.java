package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a301pro.Utilities.GetUserFromDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.utilities.Utilities;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows user to view the personal information as well as change the contact information
 */
public class ViewUserProfile extends AppCompatActivity {
    EditText phoneShow;
    EditText userFirstNameShow;
    EditText userLastNameShow;
    EditText emailShow;
    Button edit;
    Button profileQuit;
    Button lent;
    Button deny;
    String Tag = "ViewUserProfile";
    String firstName;
    String lastName;
    String phoneNumber;
    String email;
    String username;
    String bookid;

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
        lent = findViewById(R.id.lent);
        deny = findViewById(R.id.deny);


        // get the username of current user
        username = GetUserFromDB.getUsername();
        // get the username of other user, and disable profile edition
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            username = bundle.getString("USERNAME");
            bookid = bundle.getString("BOOKID");
            edit.setVisibility(View.GONE);

        }else{
            lent.setVisibility(View.GONE);
            lent.setEnabled(false);
            deny.setVisibility(View.GONE);
            deny.setEnabled(false);
        }
        DocumentReference docRef = db.collection("userDict").document(username);

        phoneShow.setEnabled(false);
        userFirstNameShow.setEnabled(false);
        userLastNameShow.setEnabled(false);
        emailShow.setEnabled(false);

        lent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users")
                        .document( GetUserFromDB.getUserID())
                        .collection("Request")
                        .document(bookid)
                        .update("status","Accepted");

                db.collection("Users")
                        .document( GetUserFromDB.getUserID())
                        .collection("MyBooks")
                        .document(bookid)
                        .update("status","Accepted");

                db.collection("userDict")
                        .document(username).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String temp = documentSnapshot.getString("UID").toString();
                                db.collection("Users")
                                        .document(temp)
                                        .collection("Borrowed")
                                        .document(bookid)
                                        .update("sit", "Accepted");

                            }
                        });
                db.collection("Library").document(bookid).update("sit","Borrowed");
                //在这加addmessage
                finish();
            }
        });


        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").document( GetUserFromDB.getUserID()).collection("Request").document(bookid).delete();

                db.collection("userDict")
                        .document(username).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String temp = documentSnapshot.getString("UID").toString();
                                db.collection("Users")
                                        .document(temp)
                                        .collection("Borrowed")
                                        .document(bookid)
                                        .delete();
                            }
                        });
                //在这加addmessage
                finish();
            }
        });

        // get user data
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        firstName = document.getString("firstName");
                        Log.i(Tag, "First name"+firstName);

                        lastName = document.getString("lastName");
                        Log.i(Tag, "Last name"+lastName);

                        email = document.getString("email");
                        Log.i(Tag, "email"+document.getString("email"));

                        phoneNumber = document.getString("phoneNumber");
                        Log.i(Tag, "phoneNumber"+document.getString("phoneNumber"));

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
        // The first click would change the interface to edit mode
        // The second click would submit the changes to user profile
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit.getText().toString().equals("Confirm")) {
                    edit.setText("Confirm");
                    phoneShow.setEnabled(true);
                    userFirstNameShow.setEnabled(true);
                    userLastNameShow.setEnabled(true);
                    emailShow.setEnabled(true);
                } else {
                    final boolean[] profileChangeSuccess = {true};
                    final String newPhoneNumber = phoneShow.getText().toString();
                    String newFirstName = userFirstNameShow.getText().toString();
                    String newLastName = userLastNameShow.getText().toString();
                    final String newEmail = emailShow.getText().toString();

                    // Change users' phone number
                    if (!newPhoneNumber.equals(phoneNumber)) {
                        if (newPhoneNumber.length() > 0) {
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("phoneNumber", newPhoneNumber);
                            db.collection("Users")
                                    .document(GetUserFromDB.getUserID())
                                    .update(userInfo);
                            db.collection("userDict")
                                    .document(username)
                                    .update(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ViewUserProfile.this,
                                                    "Phone number successfully updated!", Toast.LENGTH_SHORT).show();
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
                        } else {
                            profileChangeSuccess[0] = false;
                            phoneShow.setError("Please enter a new phone number!");
                        }
                    }

                    // Change user's full name
                    if((!newFirstName.toLowerCase().equals(firstName.toLowerCase())) ||
                            (!newLastName.toLowerCase().equals(lastName.toLowerCase()))){
                        if ((newFirstName.length() > 0) && (newLastName.length()>0)){
                            Map<String, Object> userName = new HashMap<>();
                            userName.put("firstName", newFirstName.toLowerCase());
                            userName.put("lastName", newLastName.toLowerCase());
                            db.collection("Users")
                                    .document(GetUserFromDB.getUserID())
                                    .update(userName);
                            db.collection("userDict")
                                    .document(username)
                                    .update(userName)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ViewUserProfile.this,"User's full name successfully updated!",
                                                    Toast.LENGTH_SHORT).show();
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
                        } else {
                            profileChangeSuccess[0] = false;
                            if ((newLastName.length() == 0)) {
                                userLastNameShow.setError("Your last name cannot be empty!");
                            }
                            if ((newFirstName.length() == 0)) {
                                userFirstNameShow.setError("Your first name cannot be empty!");
                            }
                        }
                    }

                    // Change users' email
                    if (!newEmail.toLowerCase().equals(email.toLowerCase())) {
                        if (newEmail.length() > 0) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(newEmail.toLowerCase())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Map<String, Object> userEmail = new HashMap<>();
                                                String curUID = GetUserFromDB.getUserID();

                                                userEmail.put("email", newEmail.toLowerCase());
                                                userEmail.put("firstName", firstName.toLowerCase());
                                                userEmail.put("lastName", lastName.toLowerCase());
                                                userEmail.put("phoneNumber", phoneNumber);
                                                userEmail.put("UID", curUID);
                                                db.collection("Users")
                                                        .document(curUID)
                                                        .update(userEmail);
                                                db.collection("userDict")
                                                        .document(username)
                                                        .update(userEmail);
                                                Toast.makeText(ViewUserProfile.this,
                                                        "User's email successfully updated!",
                                                        Toast.LENGTH_SHORT).show();
                                                Log.d("Change email#", "User's email successfully changed!");
                                            } else {
                                                profileChangeSuccess[0] = false;
                                                emailShow.setError("Email existed, try another one!");
                                                Toast.makeText(ViewUserProfile.this,
                                                        "Email existed, try another one!",
                                                        Toast.LENGTH_SHORT).show();
                                                Log.w("Change email#", "Error change user's email");
                                            }
                                        }
                                    });
                        } else {
                            profileChangeSuccess[0] = false;
                            emailShow.setError("Your email address cannot be empty!");
                        }
                    }

                    if(profileChangeSuccess[0]){
                        edit.setText("EDIT PROFILE");
                        finish();
                    }
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
}