package com.example.a301pro.Utilities;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a301pro.Book;
import com.example.a301pro.Borrowed;
import com.example.a301pro.R;
import com.example.a301pro.Request;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FilterMenu {
    public static void mybookFilter(final View view, final ArrayList<Book> bookDataList, final ArrayAdapter<Book> bookAdapter) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu_mybook, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                final CollectionReference collectionReference = db.collection("Users")
                        .document(GetUserFromDB.getUserID())
                        .collection("MyBooks");
                bookDataList.clear();
                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    public static void requestFilter(final View view, final ArrayList<Request> reqDataList, final ArrayAdapter<Request> reqAdapter) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu_pending, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {

                return false;
            }
        });
        popupMenu.show();
    }

    public static void borrowedFilter(final View view, final ArrayList<Borrowed> pendDataList, final ArrayAdapter<Borrowed> pendAdapter) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu_pending, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {

                return false;
            }
        });
        popupMenu.show();
    }
}
