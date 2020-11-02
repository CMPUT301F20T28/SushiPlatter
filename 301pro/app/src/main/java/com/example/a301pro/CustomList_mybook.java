package com.example.a301pro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CustomList_mybook extends ArrayAdapter<Book> {
    private ArrayList<Book> books;
    private Context context;
    final FirebaseStorage storage = FirebaseStorage.getInstance();

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

        final ImageView imageView = view.findViewById(R.id.book_image);
        TextView book_name = view.findViewById(R.id.name_text);
        TextView des = view.findViewById(R.id.des_text);
        TextView sta = view.findViewById(R.id.status_text);
        TextView bor = view.findViewById(R.id.borrower_text);

        //imageView.setImageResource(R.drawable.ic_image1);
        StorageReference imageRef = storage.getReference().child(book.getImageID());
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap);
                    }
                });

        book_name.setText(book.getBook_name());
        des.setText(book.getDescription());
        sta.setText(book.getStatus());
        bor.setText(book.getBorrower_name());

        return view;
    }

}
