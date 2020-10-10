package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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
        pendList = view.findViewById(R.id.pending_list);
        pendDataList = new ArrayList<>();

        final String []book_name = {"Edmonton", "Vancouver", "Toronto", "Hamilton", "Denver", "Los Angeles"};
        final String []des = {"1232311111111111111111111111111113asdffffffffaea1231231","44231231eeeeeeeeeeeeeeefddddddddddddddddddddddddddd234","55123wwwwwwwwwwwwwwww1235",
                "2221qqqqqqqqqqqqqqqqqqqqqqqqqqqqrrrrrrrrrrrrrrrrrrrrrrrrrrewwwwwwwwwwwwwwwww3123123","11231aaaaaaaaaaaaaaaaaaaddddddddddddffffff2311","22123123asssssssssssssssdddddddd1232"};
        final String []sta = {"AV","B","R","R","AC","R"};
        final String []own = {"Shanzhi ZHang","Fan","HIHIHIHI","ZHi","Shen","UUUUUUUUUUUUUUUUUUUUU"};

        for (int i = 0; i < book_name.length; i++) {
            pendDataList.add(new Borrowed(book_name[i], des[i], sta[i],own[i]));
        }

        pendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),scan_ISBN.class);
                startActivity(intent);
            }
        });

        pendAdapter = new CustomList_pending(getContext(),pendDataList);
        pendList.setAdapter(pendAdapter);

        return view;
    }

}
