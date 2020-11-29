package com.example.a301pro.Utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class CheckMessageStatus {

    public int CheckMessageStatus(String UID) {
        final int[] unRead = {0};
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users").document(UID).collection("Messages");


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                int unRead = 0;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    String readStatus = (String) doc.getData().get("readStatus");

                    if (readStatus.equals("new")){
                        unRead = 1;
                    }
                }
            }
        });

        return unRead[0];
    }
}