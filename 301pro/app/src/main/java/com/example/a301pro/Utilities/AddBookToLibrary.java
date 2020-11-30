package com.example.a301pro.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a301pro.Models.Share;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This tool will add all available books to the share library
 */
public class AddBookToLibrary {
    private Share sharedBook;
    private int imageId;
    private String book_name;
    private String author;
    private String des;
    private String owner_UID;
    private String owner;
    private String ISBN;
    private String book_id;
    private String TAG = "AddBookToLibrary";

    protected FirebaseFirestore db;

    /**
     * Add all sharable books to the share libary
     * @param sharedBook sharable book
     * @param book_id book id of the sharable book
     */
    public AddBookToLibrary(Share sharedBook, String book_id){
        AddBookToDB(sharedBook, book_id);
    }

    /**
     * Add all sharable books to the database
     * @param sharedBook sharable book
     * @param book_id book id of the sharable book
     */
    public void AddBookToDB(Share sharedBook, String book_id) {
        db = FirebaseFirestore.getInstance();
        final CollectionReference CollectRef = db.collection("Library");
        CollectRef
                .document(book_id)
                .set(sharedBook)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Book has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Book could not be added!" + e.toString());
                    }
                });
    }
}
