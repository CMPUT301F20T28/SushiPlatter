package com.example.a301pro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewRequestSender extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.request_sender);
        AppCompatAcitiviy:getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    }
}
