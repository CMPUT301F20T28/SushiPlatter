package com.example.a301pro;

import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.Utilities.RequestNotification;
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

import com.google.firebase.firestore.GeoPoint;
/**
 * This class allow user to send a book request in the purpose of book trading
 */
public class SentRequestIntent extends AppCompatActivity {
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
        final String userID = GetUserFromDB.getUserID();
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
                        Request requestInfo = new Request(bookID, RBook.getImageId(), RBook.getISBN(),
                                RBook.getBook_name(), RBook.getDes(), "Pending",
                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),new GeoPoint(53.5,-113.5));
                        new RequestNotification(requestInfo, CollectRef, RBook.getOwner());
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
}
