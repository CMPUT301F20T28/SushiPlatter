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
 * This class control the data view of shareable book list
 */
public class CustomListShare extends ArrayAdapter<Share> {
    private ArrayList<Share> shares;
    private Context context;
    final FirebaseStorage storage = FirebaseStorage.getInstance();

    /**
     * Constructor
     * @param context context of view
     * @param shares list of shareable book
     */
    public CustomListShare(@NonNull Context context, ArrayList<Share> shares) {
        super(context,0,shares);
        this.shares = shares;
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
            view = LayoutInflater.from(context).inflate(R.layout.content_share, parent, false);
        }

        Share share = shares.get(position);
        final ImageView img = view.findViewById(R.id.book_image_share);
        TextView bookName = view.findViewById(R.id.name_text_share);
        TextView des = view.findViewById(R.id.des_text_share);
        TextView sta = view.findViewById(R.id.status_text_share);
        TextView bor = view.findViewById(R.id.owner_text_share);

        StorageReference imageRef = storage.getReference().child(share.getImageId());
        imageRef.getBytes(1024 * 1024)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        img.setImageBitmap(bitmap);
                    }
                });

        bookName.setText(share.getBook_name());
        des.setText(share.getDes());
        sta.setText(share.getSit());
        bor.setText(share.getOwner());

        sta.setTextColor(context.getResources().getColor(R.color.staAvailable));
        return view;
    }
}
