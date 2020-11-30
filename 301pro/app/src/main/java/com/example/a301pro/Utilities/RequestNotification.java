package com.example.a301pro.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a301pro.Models.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is tool class for showing notification to user
 */
public class RequestNotification {
    Request requestInfo;
    protected FirebaseFirestore db;
    public static final String TAG = "Request Notification";

    /**
     * This controls and popup notification to user when there are trading request
     * @param requestInfo request information
     * @param CollectRef collection reference for request in firebase
     * @param bookOwner book owner data
     */
    public RequestNotification(final Request requestInfo, final CollectionReference CollectRef, final String bookOwner){

        final String bookID = requestInfo.getBookID();
        String[] bookIDSplit = bookID.split("-");
        final String OwnerID = bookIDSplit[0];
        final String bookName = requestInfo.getBook_name();
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

                String senderUserName = requestInfo.getRequestFrom();
                String message = senderUserName + " wants to borrow <" + bookName + "> from you.";
                new SendMessage(senderUserName, bookOwner, message);

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
