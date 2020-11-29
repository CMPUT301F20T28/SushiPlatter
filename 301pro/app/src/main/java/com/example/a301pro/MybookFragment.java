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


import com.example.a301pro.Utilities.CheckMessageStatus;
import com.example.a301pro.Utilities.FilterMenu;
import com.example.a301pro.Utilities.GetUserFromDB;
import com.example.a301pro.Utilities.UpdateMessageNotificationStatus;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import static android.app.Activity.RESULT_OK;

/**
 * This fragment class allows user to add/edit/delete a book, as well as view all the owned book.
 */
public class MybookFragment extends Fragment implements ComfirmDialog.OnFragmentInteractionListenerComfirm {
    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    ArrayList<Book> bookDataList;
    ArrayList<Book> searchList;

    public static final int REQUEST_ADD = 0;
    public static final int REQUEST_EDIT = 1;

    /**
     * Provide functionality for viewing and updating data of a book
     * @param inflater layout of the view
     * @param container layout container of view object
     * @param savedInstanceState data of previous instance
     * @return layout of the fragment
     */
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_book_fragment, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.menuBackground),
                false);
        bookList = view.findViewById(R.id.my_book_list);
        bookDataList = new ArrayList<>();
        searchList = new ArrayList<>();
        bookAdapter = new CustomListMybook(getContext(), bookDataList);
        bookList.setAdapter(bookAdapter);
        final Button filterBtn = view.findViewById(R.id.filter);
        final ImageButton mesBtn = view.findViewById(R.id.message_center);
        final FloatingActionButton addBookbtn = view.findViewById(R.id.add_book_button);
        final EditText search = view.findViewById(R.id.search_method);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Users")
                .document(GetUserFromDB.getUserID())
                .collection("MyBooks");
        final CollectionReference LibraryReference = db.collection("Library");

        // read book data from database
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                bookDataList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    String imageID = (String) doc.getData().get("imageID");
                    String bookName= (String) doc.getData().get("book_name");
                    String author = (String) doc.getData().get("author");
                    String ISBN =  (String) doc.getData().get("isbn");
                    String description = (String) doc.getData().get("description");
                    String status = (String) doc.getData().get("status");
                    String bookid = doc.getId();
                    String borrower = (String) doc.getData().get("borrower_name");
                    String owner = (String) doc.getData().get("owner");
                    bookDataList.add((new Book(imageID, bookName, author, ISBN, description, status,
                            bookid, borrower, owner)));

                }
                bookAdapter.notifyDataSetChanged();
            }
        });

        // goto user profile
        final Button gotoProfile = view.findViewById(R.id.userProfile);
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
                bookDataList.clear();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                            String imageID = (String) doc.getData().get("imageID");
                            String bookName= (String) doc.getData().get("book_name");
                            String author = (String) doc.getData().get("author");
                            String ISBN =  (String) doc.getData().get("isbn");
                            String description = (String) doc.getData().get("description");
                            String status = (String) doc.getData().get("status");
                            String bookid = doc.getId();
                            String borrower = (String) doc.getData().get("borrower_name");
                            String owner = (String) doc.getData().get("owner");
                            if (description.contains(des)|| bookName.contains(des)) {
                                bookDataList.add((new Book(imageID, bookName, author, ISBN,
                                        description, status, bookid, borrower, owner)));
                            }
                        }
                        bookAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

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

        // click on filter button to filter out item
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterMenu.mybookFilter(view, bookDataList, bookAdapter);
            }
        });

        // click circle button to add an item
        addBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditIntent.class);
                startActivityForResult(intent, REQUEST_ADD);
            }
        });

        // click an item to edit
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book myBook = bookAdapter.getItem(position);
                Intent intent = new Intent(getActivity(),AddEditIntent.class);
                intent.putExtra("BOOK", myBook);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });

        // long click item to delete
        bookList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = bookAdapter.getItem(position);
                ComfirmDialog dialog = new ComfirmDialog(book, collectionReference, LibraryReference);
                dialog.show(getFragmentManager(),"show mes");
                return true;
            }
        });
        return view;
    }

    /**
     * Receive result from AddEditIntent
     * @param requestCode request for AddEditIntent
     * @param resultCode result from AddEditIntent
     * @param data book data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            bookAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Update book data to list view
     * @param book book data
     */
    @Override
    public void onOkPressed(final Book book) {
        bookAdapter.remove(book);
    }

}