package com.example.a301pro.Utilities;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a301pro.Models.Book;
import com.example.a301pro.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * This tool provide functionality of fetching own books by selected book status
 */
public class FilterMenu {
    public static void mybookFilter(final View view, final ArrayList<Book> bookDataList, final ArrayAdapter<Book> bookAdapter) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            /**
             * Get books with matched status from the database
             * @param item selected status
             * @return boolean of the action
             */
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Users")
                        .document(GetUserFromDB.getUserID())
                        .collection("MyBooks");
                bookDataList.clear();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    /**
                     * Get data of the book
                     * @param queryDocumentSnapshots fetched data
                     * @param error fail message
                     */
                    @Override
                    public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots,
                                        @Nullable FirebaseFirestoreException error) {
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
                            if (status.equals(item.getTitle())) {
                                bookDataList.add((new Book(imageID, bookName, author, ISBN,
                                        description, status, bookid, borrower, owner)));
                            }
                        }
                        bookAdapter.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });
        popupMenu.show();
    }
}
