package com.example.a301pro;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class AddEditIntent extends AppCompatActivity {
//    private Book add_newBook;
//    private Book edit_existedBook;

    private EditText bookName, authorName, ISBN, description;
    private ImageButton img;
    final static String TAG = "AddEdit";
    final String USER = "Somebody's book";
    /** may need a confirm button instead of the status button**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_intent);
        // Get the Intent that started this activity and extract the string
        AppCompatAcitiviy:getSupportActionBar().hide();

        bookName = findViewById(R.id.book_name_editText);
        authorName = findViewById(R.id.author_editText);
        ISBN = findViewById(R.id.ISBN_editText);
        description = findViewById(R.id.description);
        img = findViewById(R.id.add_edit_image);
        Button okBtn = findViewById(R.id.book_status);
        Button backBtn = findViewById(R.id.add_edit_quit);
        Button camera = findViewById(R.id.scan_description);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Mybook");

        // open camera to take photo of the book, NOT DONE YET*********
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                startActivity(takePhotoIntent);
            }
        });

        // change btn status -> ok? need extra field for selecting status*********
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myBookName = bookName.getText().toString();
                final String myBookAuthor = authorName.getText().toString();
                final String myISBN = ISBN.getText().toString();
                final String myDes = description.getText().toString();
                HashMap<String, String> data = new HashMap<>();

                // validation of book data, book name, author name, and ISBN are required.
                if(!(TextUtils.isEmpty(myBookName)) &&
                        !(TextUtils.isEmpty(myBookAuthor)) &&
                        !(TextUtils.isEmpty(myISBN))) {

                    data.put("Book Name", myBookName);
                    data.put("Author Name", myBookAuthor);
                    data.put("ISBN", myISBN);
                    data.put("Description", myDes);
                    collectionReference
                            .document(USER)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log.d(TAG, "Book has been updated successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d(TAG, "Book could not be updated!" + e.toString());
                                }
                            });
                } else {
                    if (TextUtils.isEmpty(myBookName)){
                        bookName.setError("Book name is required!");
                    }
                    if (TextUtils.isEmpty(myBookAuthor)){
                        authorName.setError("Author name is required!");
                    }
                    if (TextUtils.isEmpty(myISBN)){
                        ISBN.setError("ISBN is required!");
                    }
                    Toast.makeText(getApplicationContext(), "Fail. Please fill the required field",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

//        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
//                    FirebaseFirestoreException error) {
//                // Clear the old list
//                cityDataList.clear();
//                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
//                {
//                    Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
//                    String city = doc.getId();
//                    String province = (String) doc.getData().get("Province Name");
//                    // Adding the cities and provinces from FireStore
//                    cityDataList.add(new City(city, province));
//                }
//                // Notifying the adapter to render any new data fetched from the cloud
//                booAdapter.notifyDataSetChanged();
//            }
//        });
    }
}
