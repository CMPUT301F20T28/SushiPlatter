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
import com.example.a301pro.Utilities.GetUserFromDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * This class allows user to scan the ISBN code of the book to get description
 * as well as for the confirmation of book trading
 */
public class ScanISBN extends AppCompatActivity implements View.OnClickListener {

    Button scanBtn;
    private String ISBN;
    private String Book_id;
    private String Name;
    private String person;
    private String sta;
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
        Intent intent =getIntent();

        Book_id = intent.getStringExtra("BOOK_ID");
        ISBN = intent.getStringExtra("ISBN_CODE");
        Name = intent.getStringExtra("NAME");
        person = intent.getStringExtra("PERSON");
        sta = intent.getStringExtra("STATUS");

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
                if (result.getContents().equals(ISBN)) {

                    if(person.equals("Borrower") && sta.equals("Accepted")) {

                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("userDict")
                                .document(Name).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String temp = documentSnapshot.getString("UID").toString();

                                        db.collection("Users")
                                                .document(temp)
                                                .collection("MyBooks")
                                                .document(Book_id)
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String sss = documentSnapshot.getString("status");
                                                if (sss.equals("Borrowed")){
                                                    db.collection("Users")
                                                            .document(GetUserFromDB.getUserID())
                                                            .collection("Borrowed")
                                                            .document(Book_id)
                                                            .update("status", "Borrowed");

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                                    builder.setMessage("You have successfully borrowed the book");
                                                    builder.setTitle("Scanning Result");
                                                    builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });

                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                                    builder.setMessage("The owner do not comfirm the lent");
                                                    builder.setTitle("Scanning Result");
                                                    builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });

                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            }
                                        });
                                    }
                                });

                    }

                    if (person.equals("Owner")&&sta.equals("Accepted")){

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("You have successfully lent the book");
                        builder.setTitle("Scanning Result");
                        builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("Users")
                                .document(GetUserFromDB.getUserID())
                                .collection("Request");
                        collectionReference.document(Book_id).update("status","Borrowed");

                        db.collection("Users")
                                .document(GetUserFromDB.getUserID())
                                .collection("MyBooks")
                                .document(Book_id).update("status","Borrowed");

                    }

                    if (person.equals("Borrower")&&sta.equals("Borrowed")){
                        final FirebaseFirestore db = FirebaseFirestore.getInstance();
                        final CollectionReference collectionReference = db.collection("Users")
                                .document(GetUserFromDB.getUserID())
                                .collection("Borrowed");
                        collectionReference.document(Book_id).update("status","Available");
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("You have successfully return the book");
                        builder.setTitle("Scanning Result");
                        builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    if (person.equals("Owner")&&sta.equals("Borrowed")){

                        db.collection("userDict")
                                .document(Name).get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        final String temp = documentSnapshot.getString("UID").toString();

                                        db.collection("Users")
                                                .document(temp)
                                                .collection("Borrowed")
                                                .document(Book_id)
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                String sss = documentSnapshot.getString("sit");
                                                if (sss.equals("Available")){
                                                    db.collection("Users")
                                                            .document(temp)
                                                            .collection("Borrowed")
                                                            .document(Book_id)
                                                            .delete();
                                                    db.collection("Users")
                                                            .document(GetUserFromDB.getUserID())
                                                            .collection("Request")
                                                            .document(Book_id)
                                                            .delete();
                                                    db.collection("Users")
                                                            .document(GetUserFromDB.getUserID())
                                                            .collection("MyBooks")
                                                            .document(Book_id)
                                                            .update("status","Available");
                                                    db.collection("Library")
                                                            .document(Book_id)
                                                            .update("sit","Available");

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                                    builder.setMessage("You have successfully close the trade");
                                                    builder.setTitle("Scanning Result");
                                                    builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });

                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }else{
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                                    builder.setMessage("The Borrower do not return the book");
                                                    builder.setTitle("Scanning Result");
                                                    builder.setNegativeButton("finish", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    });

                                                    AlertDialog dialog = builder.create();
                                                    dialog.show();
                                                }
                                            }
                                        });
                                    }
                                });
                    }
                } else {
                    if (person.equals("Borrower")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Failure borrowed the book");
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

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    if (person.equals("Owner")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Failure get the book back"+ISBN);
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

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            } else {
                Toast.makeText(this, "No result", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
