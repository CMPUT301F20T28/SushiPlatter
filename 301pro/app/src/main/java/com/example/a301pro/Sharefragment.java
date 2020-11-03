package com.example.a301pro;

import android.app.MediaRouteActionProvider;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.githang.statusbar.StatusBarCompat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Sharefragment extends Fragment {

    ListView shareList;
    ArrayAdapter<Share> shareAdapter;
    ArrayList<Share> shareDataList;
    public Sharefragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_fragment,container,false);
        StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.menuBackground),false);
        shareList = view.findViewById(R.id.search_list);
        shareDataList = new ArrayList<>();
        final Button filter_btn = view.findViewById(R.id.filter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filter_btn);
            }
        });
        // initialize a database
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        // find the reference of Library
        CollectionReference collectionReference = db.collection("Library");
        // get an instance of FirebaseStorage
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // get the reference of storage instance
        final StorageReference storageRef = storage.getReference();

        // read data from library and storage, and save read data in the shareDatalist
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            // read data from library and storage, and save read data in the shareDatalist

            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                // read data from library and storage, and save read data in the shareDatalist

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    //get bookid, book id is the main key of a book entity
                    String bookid = doc.getId();
                    // get book name
                    String bookName= (String) doc.getData().get("book_name");
                    // get book description
                    String description = (String) doc.getData().get("des");
                    // get book status
                    String status = (String) doc.getData().get("sit");
                    // get book onwer
                    String owner = (String) doc.getData().get("owner");
                    // get image of the book
                    String imageID = (String) doc.getData().get("imageID");
                    // create instance of share based on above data and add it to shareDatalist
                    shareDataList.add((new Share(imageID,bookName,description,status,owner)));
                }
                // notify the adpter
                shareAdapter.notifyDataSetChanged();

            }
        });

        shareList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),sentrequestintent.class);
                startActivity(intent);
            }
        });
        final ImageButton mes_btn = view.findViewById(R.id.message_center);
        mes_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mes_btn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
            }
        });

        shareAdapter = new CustomList_Share(getContext(),shareDataList);
        shareList.setAdapter(shareAdapter);

        return view;
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.book_category, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });

        popupMenu.show();
    }

}
