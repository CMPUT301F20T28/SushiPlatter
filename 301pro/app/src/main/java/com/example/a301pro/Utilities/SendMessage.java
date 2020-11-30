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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * This controls to send message to user for notification
 */
public class SendMessage {
    private String senderUserName;
    private String receiverUserName;
    private String message;
    protected FirebaseFirestore db;
    String TAG = "send a message";

    /**
     * Provide functionality to send message notification
     * @param senderUserName username of message sender
     * @param receiverUserName username of the user to receive message
     * @param message message to receive
     */
    public SendMessage(final String senderUserName, final String receiverUserName, final String message){
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
                        addMessageToDB(UID ,senderUserName , message, receiverUserName);
                    }
                }
            }
        });
    }

    /**
     * Add message to database under corresponding user
     * @param UID user id
     * @param senderUserName username of the sender
     * @param message message as a string
     * @param receiverUserName username of the receive
     */
    public void addMessageToDB(String UID, String senderUserName, String message, String receiverUserName){
        final CollectionReference CollectRef = db.collection("Users");
        Map<String, Object> messageMap = new HashMap<>();

        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String mstTime = ts.toString();
        String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));

        messageMap.put("sender", senderUserName);
        messageMap.put("message", message);
        messageMap.put("time", mstTime);
        messageMap.put("timeStamp", timeStamp);
        messageMap.put("receiver", receiverUserName);
        messageMap.put("readStatus", "new");
        messageMap.put("messageNotificationStatus", "not_sent");

        CollectRef
                .document(UID)
                .collection("Messages")
                .document(timeStamp)
                .set(messageMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Message has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Message could not be added!" + e.toString());
                    }
                });
    }


}
