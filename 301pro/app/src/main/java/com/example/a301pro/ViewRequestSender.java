package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * This class get the list of all request senders and their general information
 */
public class ViewRequestSender extends AppCompatActivity {
    ListView senderList;
    ArrayAdapter<User> senderAdapter;
    ArrayList<User> senderDataList;

    /**
     * User event listener of all features
     * @param savedInstanceState data passed from previous activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_request_senders);
        AppCompatAcitiviy:getSupportActionBar().hide();

        senderList = findViewById(R.id.request_sender_list);
        senderDataList = new ArrayList<>();
        senderAdapter = new CustomListRequestSender(getBaseContext(), senderDataList);
        senderList.setAdapter(senderAdapter);

        final String allSenders = getIntent().getExtras().getString("REQUEST_SENDERS");
        final String Bookid = getIntent().getExtras().getString("BOOKID");
        String[] senderArr = splitUser(String.valueOf(allSenders));

        for (String s : senderArr) {
            getSenderInfo(s);
        }

        // goto user profile of the sender
        senderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = senderAdapter.getItem(position);
                Intent intent = new Intent(getBaseContext(), ViewUserProfile.class);
                intent.putExtra("USERNAME", user.getUserName());
                intent.putExtra("BOOKID",Bookid);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Split the string of requester into an array by dash
     * @param allSenders a string that contain the username of all requester of a book
     * @return an array contain all the requester's username
     */
    String[] splitUser(String allSenders) {
        return allSenders.split("-");
    }

    private void getSenderInfo(final String username) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("userDict").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc != null) {
                        String firstName = doc.getString("firstName") ;
                        String lastName = doc.getString("lastName") ;
                        String phone = doc.getString("phoneNumber");
                        String email = doc.getString("email");
                        senderDataList.add(new User(username, email, null, firstName, lastName, phone, null));
                        senderAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
