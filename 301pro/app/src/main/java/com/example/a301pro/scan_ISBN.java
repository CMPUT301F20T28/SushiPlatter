package com.example.a301pro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * This class allows user to scan the ISBN code of the book to get description
 * as well as for the confirmation of book trading
 */
public class scan_ISBN extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn;
    private Book newBook;

    /**
     * Provide functionality for scanning isbn
     * @param savedInstanceState layout of the fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_intent);
        AppCompatAcitiviy:getSupportActionBar().hide();
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);
    }

    /**
     * controller of scan code
     * @param v layout of the view
     */
    @Override
    public void onClick(View v) {
        scanCode();
    }

    /**
     * scan code
     */
    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scaning Code");
        integrator.initiateScan();

    }

    /**
     * Receive result from Scanning
     * @param requestCode request for Scanning
     * @param resultCode result from Scanning
     * @param data description data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(result.getContents());
                builder.setTitle("Scanning Result");
                builder.setPositiveButton("Scan Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scanCode();
                    }
                }).setNegativeButton("finish", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();
                newBook = new Book(null, "aaaa", "bbbb", "cccc","dddd", "eeee", "123", "fff", null);
            }
            else {
                Toast.makeText(this,"No result",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }
}
