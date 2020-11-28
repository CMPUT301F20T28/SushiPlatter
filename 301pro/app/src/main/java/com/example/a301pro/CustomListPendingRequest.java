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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * This class control the data list view of book requesting
 */
public class CustomListPendingRequest extends ArrayAdapter<Request> {
    private ArrayList<Request> pends;
    private Context context;
    final FirebaseStorage storage = FirebaseStorage.getInstance();

    /**
     * Constructor
     * @param context context of view
     * @param pends list of books that have requests
     */
    public CustomListPendingRequest(@NonNull Context context, ArrayList<Request> pends) {
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

        final Request pend = pends.get(position);
        final ImageView img = view.findViewById(R.id.book_image_pending);
        final TextView book_name = view.findViewById(R.id.name_text_pending);
        final TextView des = view.findViewById(R.id.des_text_pending);
        final TextView sta = view.findViewById(R.id.status_text_pending);
        final TextView own = view.findViewById(R.id.owner_text_pending);

        final Button map = view.findViewById(R.id.map);
        Button scan = view.findViewById(R.id.scan_isbn);

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"111111111111",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),SetMapActivity.class);
                intent.putExtra("BOOKID",pend.getBookID());
                intent.putExtra("BORROWER",pend.getRequestFrom());
                context.startActivity(intent);
//                ((Activity)(context)).startActivityForResult(intent,MAP_SET);

            }
        });


        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"222222222222",Toast.LENGTH_SHORT).show();
                //Intent intent =new Intent(getContext(),ScanISBN.class);
                //context.startActivity(intent);

                String isbn = pend.getISBN();
                String book_id = pend.getBookID();
                if (pend.getStatus().equals("Available")){
                    Intent intent = new Intent(getContext(), ScanISBN.class);
                    intent.putExtra("ISBN_CODE", isbn);
                    intent.putExtra("BOOK_ID", book_id);
                    context.startActivity(intent);
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
        own.setText(pend.getRequestFrom());

        // change the color of the text of status
        switch (pend.getStatus()) {
            case "Accepted":
                sta.setTextColor(context.getResources().getColor(R.color.staAccepted));
                break;
            case "Available":
                sta.setTextColor(context.getResources().getColor(R.color.staAvailable));
                break;
            case "Borrowed":
                sta.setTextColor(context.getResources().getColor(R.color.staBorrowed));
                break;
            case "Requested":
                sta.setTextColor(context.getResources().getColor(R.color.staRequested));
                break;
            case "Pending":
                sta.setTextColor(context.getResources().getColor(R.color.staPending));
                break;
        }
        return view;
    }
}
