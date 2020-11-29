package com.example.a301pro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a301pro.Utilities.SetStatusTextColor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * This class control the data list view of book pending list
 */
public class CustomListPending extends ArrayAdapter<Borrowed> {
    private ArrayList<Borrowed> pends;
    private Context context;
    final FirebaseStorage storage = FirebaseStorage.getInstance();

    /**
     * Constructor
     * @param context context of view
     * @param pends list of pending book
     */
    public CustomListPending(@NonNull Context context, ArrayList<Borrowed> pends) {
        super(context,0, pends);
        this.pends = pends;
        this.context = context;
    }

    /**
     * Update view of the fragment
     * @param position index of the book to be updated
     * @param convertView convert the view
     * @param parent parent view
     * @return updated view
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //to create the view that we want as a item, and later we will add this to the Adapter
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_pending, parent, false);
        }

        final Borrowed pend = pends.get(position);
        final ImageView img = view.findViewById(R.id.book_image_pending);
        final TextView book_name = view.findViewById(R.id.name_text_pending);
        final TextView des = view.findViewById(R.id.des_text_pending);
        final TextView sta = view.findViewById(R.id.status_text_pending);
        final TextView own = view.findViewById(R.id.owner_text_pending);
        final Button map = view.findViewById(R.id.map);
        final Button scan = view.findViewById(R.id.scan_isbn);

        // open the owner profile
        own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ViewUserProfile.class);
                intent.putExtra("OWNER", own.getText().toString());
                context.startActivity(intent);
            }
        });

        // open the map
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pend.getStatus().equals("Accepted")) {
                    Intent intent = new Intent(getContext(), ViewMapActivity.class);
                    intent.putExtra("BOOKID", pend.getBookID());
                    context.startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"The book do not be accepted by the owner",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // open camera to scan code
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isbn = pend.getISBN();
                String book_id = pend.getBookID();
                String owner = pend.getO_name();
                if (pend.getStatus().equals("Accepted")){
                    Intent intent = new Intent(getContext(), ScanISBN.class);
                    intent.putExtra("ISBN_CODE", isbn);
                    intent.putExtra("BOOK_ID", book_id);
                    intent.putExtra("NAME",owner);
                    intent.putExtra("PERSON","Borrower");
                    context.startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"The book do not be accepted by the owner",Toast.LENGTH_SHORT).show();
                }

            }
        });

        StorageReference imageRef = storage.getReference().child(pend.getImageId());
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                });

        book_name.setText(pend.getBook_name());
        des.setText(pend.getDes());
        sta.setText(pend.getStatus());
        own.setText(pend.getO_name());

        // change the color of the text of status
        SetStatusTextColor.setTextColor(view, sta, pend.getStatus());

        return view;
    }
}
