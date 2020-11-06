package com.example.a301pro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewUserProfile extends AppCompatActivity {
    TextView userFullNameShow, phoneShow, emailShow, UserNameShow;
    Button edit, profileQuit;
    String Tag = "ViewUserProfile";
    String firstName, lastName, phoneNumber, email;
    private FirebaseAuth mAuth;

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


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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