package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class borrowed_fragment extends Fragment {
    ListView pendList;
    ArrayAdapter<Borrowed> pendAdapter;
    ArrayList<Borrowed> pendDataList;

    public borrowed_fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pending_fragment, container, false);
        StatusBarCompat.setStatusBarColor(getActivity(),getResources().getColor(R.color.menuBackground),false);
        pendList = view.findViewById(R.id.pending_list);
        pendDataList = new ArrayList<>();
        final Button filterBtn = view.findViewById(R.id.filter_pending);
        // click on filter button to filter out item
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filterBtn);
            }
        });
        final EditText search = view.findViewById(R.id.search_method_pending);

        final int []logo = {R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1};
        final String []book_name = {"Edmonton", "Vancouver", "Toronto", "Hamilton", "Denver", "Los Angeles"};
        final String []des = {"1232311111111111111111111111111113asdffffffffaea1231231","44231231eeeeeeeeeeeeeeefddddddddddddddddddddddddddd234","55123wwwwwwwwwwwwwwww1235",
                "2221qqqqqqqqqqqqqqqqqqqqqqqqqqqqrrrrrrrrrrrrrrrrrrrrrrrrrrewwwwwwwwwwwwwwwww3123123","11231aaaaaaaaaaaaaaaaaaaddddddddddddffffff2311","22123123asssssssssssssssdddddddd1232"};
        final String []sta = {"AV","B","R","R","AC","R"};
        final String []own = {"Shanzhi ZHang","Fan","HIHIHIHI","ZHi","Shen","UUUUUUUUUUUUUUUUUUUUU"};


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Borrowed");
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference storageRef = storage.getReference();


        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@NonNull QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String imageid = (String) doc.getData().get("imageId") ;
                    String bookName= (String) doc.getData().get("book_name");
                    String description = (String) doc.getData().get("des");
                    String status = (String) doc.getData().get("sit");
                    String owner = (String) doc.getData().get("owner");

                    pendDataList.add((new Borrowed(imageid,bookName,description,status,owner)));
                }
                pendAdapter.notifyDataSetChanged();
            }
        });
        pendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),scan_ISBN.class);
                startActivity(intent);
            }
        });
//        for (int i = 0; i < book_name.length; i++) {
//            pendDataList.add(new Borrowed(logo[i],book_name[i], des[i], sta[i],own[i]));
//        }
        // click on message button to check message
        final ImageButton mesBtn = view.findViewById(R.id.message_center_pending);
        mesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_announcement_24));
            }
        });


        pendAdapter = new CustomList_pending(getContext(),pendDataList);
        pendList.setAdapter(pendAdapter);

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
