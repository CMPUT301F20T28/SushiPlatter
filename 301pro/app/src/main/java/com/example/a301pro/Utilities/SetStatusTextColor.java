package com.example.a301pro.Utilities;

import android.content.Context;
import android.widget.TextView;
import android.view.View;

import com.example.a301pro.R;

/**
 * This is tool class for setting the color of the text base on status
 */
public class SetStatusTextColor {
    /**
     * Set the color of the text
     * @param view layout
     * @param display text view of the status
     * @param status status as a string
     */
    public static void setTextColor(View view, TextView display, String status) {
        Context context = view.getContext();
        switch (status) {
            case "Accepted":
                display.setTextColor(context.getResources().getColor(R.color.staAccepted));
                break;
            case "Available":
                display.setTextColor(context.getResources().getColor(R.color.staAvailable));
                break;
            case "Borrowed":
                display.setTextColor(context.getResources().getColor(R.color.staBorrowed));
                break;
            case "Requested":
                display.setTextColor(context.getResources().getColor(R.color.staRequested));
                break;
            case "Pending":
                display.setTextColor(context.getResources().getColor(R.color.staPending));
                break;
        }
    }
}
