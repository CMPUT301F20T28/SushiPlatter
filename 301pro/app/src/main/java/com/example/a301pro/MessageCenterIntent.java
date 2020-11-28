package com.example.a301pro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MessageCenterIntent extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.content_message_center);
        AppCompatAcitiviy:getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    }
}
