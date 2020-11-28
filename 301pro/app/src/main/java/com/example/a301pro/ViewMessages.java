package com.example.a301pro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**

 */
public class ViewMessages extends AppCompatActivity {
    ListView messageList;
    ArrayAdapter<Message> messageAdapter;
    ArrayList<Message> messageDataList;

    /**
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_messages);
        AppCompatAcitiviy:
        getSupportActionBar().hide();

        messageList = findViewById(R.id.messages_list);
        messageDataList = new ArrayList<>();
        messageAdapter = new CostumeListMessages(getApplicationContext(), messageDataList);
        messageList.setAdapter(messageAdapter);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String userUID = getIntent().getExtras().getString("userUID");
        final CollectionReference collectionReference = db.collection("Users").document(userUID).collection("Messages");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                messageDataList.clear();

                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {

                    String sender = (String) doc.getData().get("sender");
                    String message = (String) doc.getData().get("message");
                    String timeMST = (String) doc.getData().get("time");
                    String timeStamp = (String) doc.getData().get("timestamp");
                    String receiver = (String) doc.getData().get("receiver");
                    messageDataList.add((new Message(timeStamp, timeMST, message, sender,receiver)));
                }
                messageAdapter.notifyDataSetChanged();
            }
        });
    }
}