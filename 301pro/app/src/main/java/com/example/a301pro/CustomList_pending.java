package com.example.a301pro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomList_pending extends ArrayAdapter<Borrowed> {
    private ArrayList<Borrowed> pends;
    private Context context;

    public CustomList_pending(@NonNull Context context, ArrayList<Borrowed> pends) {
        super(context,0,pends);
        this.pends = pends;
        this.context = context;
        //Constructor of the CustomList
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //to create the view that we want as a item, and later we will add this to the Adapter
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_pending, parent, false);
        }

        Borrowed pend = pends.get(position);

        TextView book_name = view.findViewById(R.id.name_text_pending);
        TextView des = view.findViewById(R.id.des_text_pending);
        TextView sta = view.findViewById(R.id.status_text_pending);
        TextView own = view.findViewById(R.id.owner_text_pending);

        book_name.setText(pend.getBook_name());
        des.setText(pend.getDes());
        sta.setText(pend.getStatus());
        own.setText(pend.getO_name());

        return view;
    }
}
