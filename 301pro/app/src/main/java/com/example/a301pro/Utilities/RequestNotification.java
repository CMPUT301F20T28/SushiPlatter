package com.example.a301pro.Utilities;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.a301pro.Request;
import com.example.a301pro.Register;
import com.example.a301pro.Sentrequestintent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class RequestNotification {
    Request requestInfo;
    protected FirebaseFirestore db;
    public static final String TAG = "Request Notification";

    public RequestNotification(final Request requestInfo, final CollectionReference CollectRef){

        final String bookID = requestInfo.getBookID();
        String[] bookIDSplit = bookID.split("-");
        final String OwnerID = bookIDSplit[0];
        final String existingRequestSender = "";

        DocumentReference docRef = CollectRef
                .document(OwnerID)
                .collection("Request")
                .document(bookID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String existingRequestSender = "";
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        existingRequestSender = document.getString("requestFrom");
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

                boolean alreadyReq = false;
                if (existingRequestSender != ""){
                    String currentRequestSender = requestInfo.getRequestFrom();
                    String[] senderList = existingRequestSender.split("-");
                    for (String element : senderList) {
                        Log.d(TAG, "Request: "+element);
                        Log.d(TAG, "Current Request: "+currentRequestSender);
                        if (element.equals(currentRequestSender)) {
                            Log.d(TAG, "Request already sent from: "+element);
                            alreadyReq = true;
                        }
                    }
                    if (alreadyReq == false){
                        requestInfo.setRequestFrom(existingRequestSender + "-" + currentRequestSender);
                    }
                }

                if (alreadyReq == true){
                    Log.d(TAG, "User request failed: " +  requestInfo.getRequestFrom()
                            + "already requested " + bookID);
                    return;
                };

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
                                Log.d(TAG, "Request Notification could not be updated!"
                                        + e.toString());
                            }
                        });
            }
        });
    }
}
