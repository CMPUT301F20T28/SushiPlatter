package com.example.a301pro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIntent extends AppCompatActivity {
    private Book add_newBook;
    private Book edit_existedBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_intent);
        // Get the Intent that started this activity and extract the string
        //隐藏title
        AppCompatAcitiviy:getSupportActionBar().hide();
        Button canmera = findViewById(R.id.scan_description);
        canmera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                startActivity(takePhotoIntent);
            }
        });
    }

}
