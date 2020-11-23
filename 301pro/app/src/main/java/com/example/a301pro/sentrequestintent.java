package com.example.a301pro;

import android.content.Intent;
import com.example.a301pro.Utilities.requestNotification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class allow user to send a book request in the purpose of book trading
 */
public class sentrequestintent extends AppCompatActivity {
    private User sender;
    private Share requestedBook;
    protected FirebaseFirestore db;
    public static final String TAG = "SentRequest";

    /**
     * Control the status and request of a book
     * @param savedInstanceState data of previous instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sent_request);
        // Get the Intent that started this activity and extract the string
        AppCompatAcitiviy:getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final Bundle bundle = getIntent().getExtras();
        // user has selected a book to edit if bundle if not empty
        requestedBook = (Share) bundle.getSerializable("R_book");   // get the item
        db = FirebaseFirestore.getInstance();
        Button sentBtn = findViewById(R.id.sent_request);
        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToDb(requestedBook);
                //Toast.makeText(getApplicationContext(),requestedBook.getBookname(),Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Button back = findViewById(R.id.back_sent_request);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    /**
     * Update book data to the database
     * @param RBook book to be requested
     */
    public void sendDataToDb(final Share RBook) {
        final CollectionReference CollectRef = db.collection("Users");
        final String userID = getUserID();
        final String bookID = RBook.getBookID();
        RBook.setSit("Requested");
        CollectRef
                .document(userID)
                .collection("Borrowed")
                .document(bookID)
                .set(RBook)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Book has been updated successfully!");
                        Request requestInfo = new Request(bookID, RBook.getImageId(), RBook.getBook_name(), RBook.getDes(), "Pending", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        new requestNotification(requestInfo, CollectRef);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Book could not be updated!" + e.toString());
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
