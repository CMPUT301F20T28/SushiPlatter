package com.example.a301pro.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a301pro.Models.Message;
import com.example.a301pro.R;

import java.util.ArrayList;

/**
 * This class control the data list view of message
 */
public class CostumeListMessages extends ArrayAdapter<Message> {
    private ArrayList<Message> messages;
    private Context context;

    /**
     * context of view
     * @param context context of view
     * @param messages a list of messages to display
     */
    public CostumeListMessages(@NonNull Context context, ArrayList<Message> messages) {
        super(context,0, messages);
        this.messages = messages;
        this.context = context;
    }

    /**
     * Update view of the fragment
     * @param position index of the message
     * @param convertView convert the view
     * @param parent parent view
     * @return updated view
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //to create the view that we want as a item, and later we will add this to the Adapter
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content_message_center, parent, false);
        }

        Message message = messages.get(position);

        TextView timeStamp = view.findViewById(R.id.messageTime);
        TextView messageDetail = view.findViewById(R.id.messageDetail);
        TextView MessageStatus = view.findViewById(R.id.new_message);

        if (message.getReadStatus().equals("new")){
            MessageStatus.setVisibility(View.VISIBLE);
        }

        else{
            MessageStatus.setVisibility(View.GONE);
        }

        timeStamp.setText(message.getTimeMST());
        messageDetail.setText(message.getMessage());

        return view;
    }
}
