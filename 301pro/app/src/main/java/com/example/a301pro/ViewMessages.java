package com.example.a301pro;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a301pro.Utilities.UpdateMessageStatus;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

        messageAdapter = new CostumeListMessages(getBaseContext(), messageDataList);

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
                    String timeStamp = (String) doc.getData().get("timeStamp");
                    String messageID = timeStamp;
                    String receiver = (String) doc.getData().get("receiver");
                    String readStatus = (String) doc.getData().get("readStatus");
                    String messageNotificationStatus = (String) doc.getData().get("messageNotificationStatus");

                    messageDataList.add((new Message(timeStamp, timeMST, message, sender,receiver, readStatus, messageNotificationStatus)));
                }
                messageAdapter.notifyDataSetChanged();
            }
        });

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messageAdapter.getItem(position);

                try{
                    String receiver = message.getReceiver();
                    String messageID = message.getTimeStamp();
                    String readStatus = message.getReadStatus();
                    if (readStatus.equals("new")){
                        new UpdateMessageStatus(receiver, messageID, "read");
                        Toast.makeText(ViewMessages.this, "Message read!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }
        });
    }
}