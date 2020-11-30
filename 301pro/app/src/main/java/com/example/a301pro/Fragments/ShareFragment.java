package com.example.a301pro.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a301pro.Adapters.CustomListShare;
import com.example.a301pro.R;
import com.example.a301pro.SentRequestIntent;
import com.example.a301pro.Models.Share;
import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.Utilities.UpdateMessageNotificationStatus;
import com.example.a301pro.View.ViewMessages;
import com.example.a301pro.View.ViewUserProfile;
import com.githang.statusbar.StatusBarCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * This fragment class allows user to view all shareable book and selects a book for trading
 */
public class ShareFragment extends Fragment {
    ListView shareList;
    ArrayAdapter<Share> shareAdapter;
    ArrayList<Share> shareDataList;
    public static final int REQUEST_REQUEST = 3;
    protected FirebaseFirestore db;


    /**
     * Default constructor
     */
    public ShareFragment() {
    }

    /**
     * Provide functionality for all shareable books
     * @param inflater layout of the view
     * @param container layout container of view object
     * @param savedInstanceState data of previous instance
     * @return layout of the fragment
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_fragment, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(),
                getResources().getColor(R.color.menuBackground),false);
        shareList = view.findViewById(R.id.search_list);
        shareDataList = new ArrayList<>();
        shareAdapter = new CustomListShare(getContext(), shareDataList);
        shareList.setAdapter(shareAdapter);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Library");
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();
        final ImageButton mesBtn = view.findViewById(R.id.message_center);
        final EditText search = view.findViewById(R.id.search_method);

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                shareDataList.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    String imageId = (String) doc.getData().get("imageId") ;
                    String bookId = doc.getId();
                    String ISBN = (String) doc.getData().get("isbn");
                    String bookName= (String) doc.getData().get("book_name");
                    String description = (String) doc.getData().get("des");
                    String status = (String) doc.getData().get("sit");
                    String owner = (String) doc.getData().get("owner");

                    if (!owner.equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())&& status.equals("Available")) {
                        shareDataList.add((new Share(bookId, imageId, ISBN, bookName,
                                description, status, owner)));
                    }
                }
                shareAdapter.notifyDataSetChanged();
            }
        });

        // goto user profile
        final Button gotoProfile = view.findViewById(R.id.userhead);
        gotoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewUserProfile.class);
                startActivity(intent);
            }
        });

        // search book by keyword
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String des = s.toString().toLowerCase();
                shareDataList.clear();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            String imageId = (String) doc.getData().get("imageId") ;
                            String bookId = doc.getId();
                            String ISBN = (String) doc.getData().get("isbn");
                            String bookName= (String) doc.getData().get("book_name");
                            String description = (String) doc.getData().get("des");
                            String status = (String) doc.getData().get("sit");
                            String owner = (String) doc.getData().get("owner");
                            if (description.contains(des) || bookName.contains(des) ) {
                                if (!owner.equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()) && status.equals("Available")) {
                                    shareDataList.add((new Share(bookId, imageId, ISBN,bookName,
                                            description, status, owner)));
                                }
                            }
                        }
                        shareAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // switch page to send borrowing request to selected book
        shareList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Share rBook = shareAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), SentRequestIntent.class);
                intent.putExtra("R_BOOK", rBook);
                startActivityForResult(intent, REQUEST_REQUEST);
            }
        });

        // Check if user received a new message
        db.collection("Users").document(GetUserFromDB.getUserID()).collection("Messages").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                int unRead = 0;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    try {
                        String readStatus = (String) doc.getData().get("readStatus");
                        String message = (String) doc.getData().get("message");
                        String messageID = (String) doc.getData().get("timeStamp");
                        String receiver = (String) doc.getData().get("receiver");
                        String messageNotificationStatus = (String) doc.getData().get("messageNotificationStatus");
                        if(readStatus.equals("new")){
                            mesBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
                            if (messageNotificationStatus.equals("not_sent")){
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                                new UpdateMessageNotificationStatus(receiver, messageID, "sent");
                            }
                        }
                    }catch (Exception e){
                        return;
                    };
                }
            }
        });

        // click on message button to open notification center for checking message
        mesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewMessages.class);
                intent.putExtra("userUID", GetUserFromDB.getUserID());
                startActivity(intent);
            }
        });

        shareAdapter = new CustomListShare(getContext(), shareDataList);
        shareList.setAdapter(shareAdapter);
        return view;
    }
}
