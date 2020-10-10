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
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class mybookfragment extends Fragment {

    ListView bookList;
    ArrayAdapter<Book> bookAdapter;
    ArrayList<Book> bookDataList;
    public mybookfragment() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_book_fragment,container,false);

        bookList = view.findViewById(R.id.my_book_list);
        bookDataList = new ArrayList<>();
        final Button filter_btn = view.findViewById(R.id.filter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filter_btn);
            }
        });

        final String []book_name = {"Edmonton", "Vancouver", "Toronto", "Hamilton", "Denver", "Los Angeles"};
        final String []autor_name = {"AB", "BC", "ON", "ON", "CO", "CA"};
        final String []ISBN = {"123","444","555","222","111","222"};
        final String []des = {"1232311111111111111111111111111113asdffffffffaea1231231","44231231eeeeeeeeeeeeeeefddddddddddddddddddddddddddd234","55123wwwwwwwwwwwwwwww1235",
                "2221qqqqqqqqqqqqqqqqqqqqqqqqqqqqrrrrrrrrrrrrrrrrrrrrrrrrrrewwwwwwwwwwwwwwwww3123123","11231aaaaaaaaaaaaaaaaaaaddddddddddddffffff2311","22123123asssssssssssssssdddddddd1232"};
        final String []sta = {"AV","B","R","R","AC","R"};
        final String []bor = {"Shanzhi ZHang","Fan","HIHIHIHI","ZHi","Shen","UUUUUUUUUUUUUUUUUUUUU"};
        for (int i = 0; i < book_name.length; i++) {
            bookDataList.add((new Book(book_name[i], autor_name[i],ISBN[i],des[i],sta[i],bor[i])));
        }

        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),AddEditIntent.class);
                startActivity(intent);
            }
        });

        bookAdapter = new CustomList_mybook(getContext(),bookDataList);
        bookList.setAdapter(bookAdapter);

        final FloatingActionButton addBookbtn = view.findViewById(R.id.add_book_button);
        addBookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new EditbookFragment().show(getActivity().getSupportFragmentManager(),"add work");
                Intent intent = new Intent(getActivity(), AddEditIntent.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void showPopupMenu(View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
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
