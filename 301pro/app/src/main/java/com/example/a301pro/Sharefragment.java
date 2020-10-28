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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Sharefragment extends Fragment {

    ListView shareList;
    ArrayAdapter<Share> shareAdapter;
    ArrayList<Share> shareDataList;

    public Sharefragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.share_fragment,container,false);

        shareList = view.findViewById(R.id.search_list);
        shareDataList = new ArrayList<>();
        final Button filter_btn = view.findViewById(R.id.filter);
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(filter_btn);
            }
        });
        final int []logo = {R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1,R.drawable.ic_image1};
        final String []share_name = {"Edmonton", "Vancouver", "Toronto"};
        final String []des = {"123231111faea1231231","4423111231eeddd234",
                "551111235"};
        final String []sta = {"AV","AV","AV"};
        final String []owner = {"Shanzhi ZHang","Fan","HIHIHIHI"};

        for (int i = 0; i < share_name.length; i++) {
            shareDataList.add((new Share(logo[i],share_name[i],des[i],sta[i],owner[i])));
        }
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
