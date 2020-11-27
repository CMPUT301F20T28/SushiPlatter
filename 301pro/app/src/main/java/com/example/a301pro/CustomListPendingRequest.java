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

/**
 * This class control the data view of book pending list
 */
public class CustomListPendingRequest extends ArrayAdapter<Request> {
    private ArrayList<Request> pends;
    private Context context;
    final FirebaseStorage storage = FirebaseStorage.getInstance();

    /**
     * Constructor
     * @param context context of view
     * @param pends list of pending book
     */
    public CustomListPendingRequest(@NonNull Context context, ArrayList<Request> pends) {
        super(context,0,pends);
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

        Request pend = pends.get(position);
        final ImageView img = view.findViewById(R.id.book_image_pending);
        TextView book_name = view.findViewById(R.id.name_text_pending);
        TextView des = view.findViewById(R.id.des_text_pending);
        TextView sta = view.findViewById(R.id.status_text_pending);
        TextView own = view.findViewById(R.id.owner_text_pending);

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

        return view;
    }
}
