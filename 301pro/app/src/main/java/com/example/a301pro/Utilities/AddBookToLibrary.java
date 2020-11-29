package com.example.a301pro.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a301pro.Models.Share;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

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

    public AddBookToLibrary(Share sharedBook, String book_id){
        AddBookToDB(sharedBook, book_id);
    }


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
