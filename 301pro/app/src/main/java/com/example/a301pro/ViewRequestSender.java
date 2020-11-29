package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a301pro.Adapters.CustomListRequestSender;
import com.example.a301pro.Models.User;
import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.Utilities.SendMessage;
import com.example.a301pro.View.ViewUserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
        final String[] senderArr = splitUser(String.valueOf(allSenders));

        for (String s : senderArr) {
            getSenderInfo(s);
        }

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Button denyall = findViewById(R.id.denyall);
        denyall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Users").document(GetUserFromDB.getUserID()).collection("Request").document(Bookid).delete();
                for (String s : senderArr) {
                    db.collection("userDict")
                            .document(s).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String temp = documentSnapshot.getString("UID").toString();
                                    db.collection("Users")
                                            .document(temp)
                                            .collection("Borrowed")
                                            .document(Bookid)
                                            .delete();
                                }
                            });
                    new SendMessage(GetUserFromDB.getUsername(), s, GetUserFromDB.getUsername().toString() + " has denied your borrow request");

                }
                finish();
            }
        });

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
