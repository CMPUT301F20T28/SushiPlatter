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

import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import static android.app.Activity.RESULT_OK;

import java.util.ArrayList;

/**
 * This fragment class allows user to borrow a book
 */
public class BorrowedFragment extends Fragment {
    ListView pendList;
    ArrayAdapter<Borrowed> pendAdapter;
    ArrayList<Borrowed> pendDataList;

    /**
     * Default constructor
     */
    public BorrowedFragment() {
    }

    /**
     * Provide functionality for borrowing a book
     * @param inflater layout of the view
     * @param container layout container of view object
     * @param savedInstanceState data of previous instance
     * @return layout of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pending_fragment, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(),
                getResources().getColor(R.color.menuBackground),false);
        pendList = view.findViewById(R.id.pending_list);
        pendDataList = new ArrayList<>();
        pendAdapter = new CustomListPending(getContext(),pendDataList);
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
                .document(getUserID()).collection("Borrowed");
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                pendDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String bookID = doc.getId();
                    String imageId = (String) doc.getData().get("imageId") ;
                    String ISBN = (String) doc.getData().get("isbn");
                    String bookName= (String) doc.getData().get("book_name");
                    String description = (String) doc.getData().get("des");
                    String status = (String) doc.getData().get("sit");
                    String owner = (String) doc.getData().get("owner");

                    pendDataList.add((new Borrowed(bookID, imageId, ISBN, bookName, description,
                            status,owner)));
                }
                pendAdapter.notifyDataSetChanged();
            }
        });

        // search book by keyword
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String dess = s.toString();
                pendDataList.clear();
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
                            if (description.contains(dess) || bookName.contains(dess)) {

                                pendDataList.add((new Borrowed(bookId, imageId, ISBN, bookName,
                                        description, status, owner)));

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

        pendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Borrowed selectedBook = pendAdapter.getItem(position);
                String isbn = selectedBook.getISBN();
                String book_id = selectedBook.getBookID();
                if (!selectedBook.getStatus().equals("Requested")){
                    Intent intent = new Intent(getContext(), ScanISBN.class);
                    intent.putExtra("ISBN_CODE", isbn);
                    intent.putExtra("BOOK_ID", book_id);
                    startActivityForResult(intent,0);
                }
//                Intent intent = new Intent(getActivity(),SetMapActivity.class);
//                startActivity(intent);
            }
        });

        // click on message button to check message
        final ImageButton mesBtn = view.findViewById(R.id.message_center_pending);
        mesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
            }
        });


        pendAdapter = new CustomListPending(getContext(),pendDataList);
        pendList.setAdapter(pendAdapter);

        return view;
    }



    /**
     * Popup the menu for picking status
     * @param view view
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.book_category, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

    /**
     * Get uid of the current logged in user
     * @return uid as a string
     */
    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * Get username of the current logged in user
     * @return username as a string
     */
    protected String getUserName(){
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }


}
