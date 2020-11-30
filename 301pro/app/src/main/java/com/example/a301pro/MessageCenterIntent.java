package com.example.a301pro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class provides functionality of viewing new messages or notifications
 */
public class MessageCenterIntent extends AppCompatActivity {

    /**
     * Provide functionality viewing new messages
     * @param savedInstanceState data
     * @return layout of the intent
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.content_message_center);
        AppCompatAcitiviy:getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    }
}
