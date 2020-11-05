package com.example.a301pro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewUserProfile extends AppCompatActivity {
    TextView usernameShow, phoneShow, emailShow;
    Button edit, profileQuit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_profile);
        AppCompatAcitiviy:getSupportActionBar().hide();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        usernameShow = findViewById(R.id.user_name_display);
        phoneShow = findViewById(R.id.phone_num_display);
        emailShow = findViewById(R.id.user_email_display);
        edit = findViewById(R.id.edit_profile);
        profileQuit = findViewById(R.id.profile_quit);

        CollectionReference collectionReference = db.collection("User");
        DocumentReference docRef = collectionReference.document(getUserID());

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