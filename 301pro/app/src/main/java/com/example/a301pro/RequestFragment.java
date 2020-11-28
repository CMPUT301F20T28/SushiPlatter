package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a301pro.Utilities.GetUserFromDB;
import com.githang.statusbar.StatusBarCompat;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * This fragment class allows the owner to view all book borrowing requests
 */
public class RequestFragment extends Fragment {
    ListView pendList;
    ArrayAdapter<Request> pendAdapter;
    ArrayList<Request> pendDataList;

    /**
     * Default constructor
     */
    public RequestFragment() {
    }

    /**
     * Provide functionality for requesting a book
     * @param inflater layout of the view
     * @param container layout container of view object
     * @param savedInstanceState data of previous instance
     * @return layout of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pending_fragment, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(),
                getResources().getColor(R.color.menuBackground), false);
        pendList = view.findViewById(R.id.pending_list);
        pendDataList = new ArrayList<>();
        pendAdapter = new CustomListPendingRequest(getContext(), pendDataList);
        pendList.setAdapter(pendAdapter);

        final EditText search = view.findViewById(R.id.search_method_pending);
        final Button filterBtn = view.findViewById(R.id.filter_pending);
        // click on filter button to filter out item
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filterBtn);
            }
        });

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users")
                .document(GetUserFromDB.getUserID())
                .collection("Request");
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                pendDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String imageId = (String) doc.getData().get("imageId") ;
                    String bookId = doc.getId();
                    String ISBN = (String) doc.getData().get("ISBN");
                    String bookName= (String) doc.getData().get("book_name");
                    String description = (String) doc.getData().get("des");
                    String status = (String) doc.getData().get("status");
                    String requestSender = (String) doc.getData().get("requestFrom");
                    GeoPoint location = doc.getGeoPoint("location");

                    pendDataList.add((new Request(bookId, imageId, ISBN, bookName, description,
                            status,requestSender,location)));
                }
                pendAdapter.notifyDataSetChanged();
            }
        });

        // goto user profile
        final Button gotoProfile = view.findViewById(R.id.userhead_pending);
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
                pendDataList.clear();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            String imageId = (String) doc.getData().get("imageId") ;
                            String bookId = doc.getId();
                            String ISBN = (String) doc.getData().get("ISBN");
                            String bookName= (String) doc.getData().get("book_name");
                            String description = (String) doc.getData().get("des");
                            String status = (String) doc.getData().get("status");
                            String requestSender = (String) doc.getData().get("requestFrom");
                            GeoPoint location = doc.getGeoPoint("location");

                            if (description.contains(des) || bookName.contains(des)) {
                                pendDataList.add((new Request(bookId, imageId, ISBN, bookName,
                                        description, status, requestSender, location)));

                            }
                        }
                        pendAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // selected a book to view all request senders
        pendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Request selectedBook = pendAdapter.getItem(position);
                if (!selectedBook.getStatus().equals("Requested")){
                    Intent intent = new Intent(getContext(), ViewRequestSender.class);
                    intent.putExtra("REQUEST_SENDERS", selectedBook.getRequestFrom());
                    intent.putExtra("BOOKID",selectedBook.getBookID());
                    startActivity(intent);
                }
            }
        });

        // click on message button to check message
        final ImageButton mesBtn = view.findViewById(R.id.message_center_pending);
        mesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
                Intent intent = new Intent(getContext(), ViewMessages.class);
                intent.putExtra("userUID", GetUserFromDB.getUserID());
                startActivity(intent);
            }
        });

        pendAdapter = new CustomListPendingRequest(getContext(), pendDataList);
        pendList.setAdapter(pendAdapter);

        return view;
    }

    /**
     * Popup the menu for filtering book by category
     * @param view view
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.show();
    }
}
