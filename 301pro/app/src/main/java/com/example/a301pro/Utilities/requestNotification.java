package com.example.a301pro.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a301pro.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class requestNotification {
    Request requestInfo;
    protected FirebaseFirestore db;
    public static final String TAG = "Request Notification";

    public requestNotification(Request requestInfo, CollectionReference CollectRef){
        String bookID = requestInfo.getBookID();
        String[] bookIDSplit = bookID.split("-");
        String OwnerID = bookIDSplit[0];
        CollectRef
                .document(OwnerID)
                .collection("Request")
                .document(bookID)
                .set(requestInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Request Notification has been updated successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Request Notification could not be updated!" + e.toString());
                    }
                });


    }
}
