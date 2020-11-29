package com.example.a301pro.Utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateMessageNotificationStatus {
    private String receiverUserName;
    private String messageID;
    private String status;

    protected FirebaseFirestore db;
    String TAG = "update message notification status";


    public UpdateMessageNotificationStatus(final String receiverUserName, final String messageID, final String status){
        db = FirebaseFirestore.getInstance();
        String receiverUID;
        CollectionReference collectionReference = db.collection("userDict");
        DocumentReference docRef = collectionReference.document(receiverUserName);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String UID = (String) document.getData().get("UID");
                        addMessageStatusToDB(receiverUserName , messageID, status, UID);
                    }
                } else {
                }
            }
        });
    }

    public void addMessageStatusToDB(String receiverUserName, String messageID, String status, String UID){
        final CollectionReference CollectRef = db.collection("Users");

        CollectRef
                .document(UID)
                .collection("Messages")
                .document(messageID)
                .update("messageNotificationStatus", status)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Message notification  status has been updates successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Message notification status could not be updated!" + e.toString());
                    }
                });
    }
}

