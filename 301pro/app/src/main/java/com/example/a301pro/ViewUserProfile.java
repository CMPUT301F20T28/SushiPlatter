package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class allows user to view the personal information as well as change the contact information
 */
public class ViewUserProfile extends AppCompatActivity {
    TextView userFullNameShow, emailShow, UserNameShow;
    EditText phoneShow;
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

        userFullNameShow = findViewById(R.id.user_name_display);
        phoneShow = findViewById(R.id.phone_num_display);
        emailShow = findViewById(R.id.user_email_display);
        edit = findViewById(R.id.edit_profile);
        profileQuit = findViewById(R.id.profile_quit);

        DocumentReference docRef = db.collection("Users").document(getUserID());
        mAuth = FirebaseAuth.getInstance();
        phoneShow.setEnabled(false);

        // get user data
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {

                        firstName = document.getString("firstName");
                        Log.i(Tag,"First name"+firstName);

                        lastName = document.getString("lastName");
                        Log.i(Tag,"Last name"+lastName);

                        email = document.getString("email");
                        Log.i(Tag,"email"+document.getString("email"));

                        phoneNumber = document.getString("phoneNumber");
                        Log.i(Tag,"phoneNumber"+document.getString("phoneNumber"));

                        phoneShow.setText(phoneNumber);
                        emailShow.setText(email);
                        userFullNameShow.setText(firstName + " " + lastName);

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
                }else{
                    String newPhoneNumber = phoneShow.getText().toString();
                    if (newPhoneNumber.equals(phoneNumber)){
                        phoneShow.setError("Please enter a new phone number!");
                    }
                    else{
                        if(newPhoneNumber.length()>0){
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("phoneNumber", newPhoneNumber);
                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .update(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Change Phone#", "User phone number successfully changed!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Change Phone#", "Error change new user phone number", e);
                                        }
                                    });
                            finish();
                        }else{
                            phoneShow.setError("Please enter a new phone number!");
                        }
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
    /**
     * Get uid of the current logged in user
     * @return uid as a string
     */
    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}