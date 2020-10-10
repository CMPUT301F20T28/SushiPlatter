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

public class CustomList_mybook extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;


    public CustomList_mybook(@NonNull Context context, ArrayList<Book> books) {
        super(context,0,books);
        this.books = books;
        this.context = context;
        //Constructor of the CustomList
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //to create the view that we want as a item, and later we will add this to the Adapter
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_mybook, parent, false);
        }

        Book book = books.get(position);

        TextView book_name = view.findViewById(R.id.name_text);
        TextView author = view.findViewById(R.id.author_text);
        TextView ISBN = view.findViewById(R.id.ISBN_text);
        TextView des = view.findViewById(R.id.des_text);
        TextView sta = view.findViewById(R.id.status_text);
        TextView bor = view.findViewById(R.id.borrower_text);

        book_name.setText(book.getBook_name());
        author.setText(book.getAuthor());
        ISBN.setText(book.getISBN());
        des.setText(book.getDescription());
        sta.setText(book.getStatus());
        bor.setText(book.getBorrower_name());

        return view;
    }

}
