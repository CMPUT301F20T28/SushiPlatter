package com.example.a301pro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a301pro.R;
import com.example.a301pro.Models.User;

import java.util.ArrayList;

/**
 * This class control the data list view of all requesting senders of a book
 */
public class CustomListRequestSender extends ArrayAdapter<User> {
    private ArrayList<User> senders;
    private Context context;

    /**
     * Constructor
     * @param context context of view
     * @param senders list of request sender of a book
     */
    public CustomListRequestSender(@NonNull Context context, ArrayList<User> senders) {
        super(context,0, senders);
        this.senders = senders;
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
            view = LayoutInflater.from(context).inflate(R.layout.content_request_sender, parent, false);
        }

        User sender = senders.get(position);

        TextView username = view.findViewById(R.id.requestSenderUsername);
        TextView fullName = view.findViewById(R.id.requestSenderName);
        TextView phone = view.findViewById(R.id.requestSenderPhone);
        TextView email = view.findViewById(R.id.requestSenderEmail);

        String fullNameCharSequence = sender.getFirstName() + " " + sender.getLastName();
        username.setText(sender.getUserName());
        fullName.setText(fullNameCharSequence);
        phone.setText(sender.getPhoneNumber());
        email.setText(sender.getEmail());

        return view;
    }
}
