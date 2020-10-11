package com.example.a301pro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class sentrequestintent extends AppCompatActivity {
    private User sender;
    private Book requested_Book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sent_request);
        // Get the Intent that started this activity and extract the string
        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();
    }

}
