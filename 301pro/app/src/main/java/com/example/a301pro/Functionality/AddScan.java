package com.example.a301pro.Functionality;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a301pro.Functionality.CaptureAct;
import com.example.a301pro.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AddScan extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn;
    protected FirebaseFirestore db;
    public static final String TAG = "SCAN_ISBN";
    /**
     * Provide functionality for scanning isbn
     * @param savedInstanceState layout of the fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_intent);
        AppCompatAcitiviy:getSupportActionBar().hide();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

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
        integrator.setPrompt("Scanning Code");
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
        if (result != null) {
            if (result.getContents() != null) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("RESULT",result.getContents().toString());
                setResult(RESULT_OK,returnIntent);
                finish();

            } else {
                Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
