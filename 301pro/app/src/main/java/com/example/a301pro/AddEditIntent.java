package com.example.a301pro;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a301pro.Models.Book;
import com.example.a301pro.Models.Share;
import com.example.a301pro.Utilities.AddBookToLibrary;
import com.example.a301pro.Utilities.BookStatusEnum;
import com.example.a301pro.Utilities.GetUserFromDB;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

/**
 * This class provides functionality of adding a new book to the collection of the user,
 * and editing the data of a selected own book.
 */
public class AddEditIntent extends AppCompatActivity {
    private EditText bookName;
    private EditText authorName;
    private EditText  ISBN;
    private EditText description;
    private TextView status;
    private ImageButton img;
    public static final String TAG = "AddEdit";
    protected FirebaseFirestore db;
    private Book myBook;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_TO_TEXT = 2;
    private boolean jg = false;
    private String imgid;
    Button camera;
    private String userName;
    private boolean removeImg = false;

    /**
     * User event listener of all features
     * @param savedInstanceState data passed from previous activity
     */
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
        status = findViewById(R.id.status);
        img = findViewById(R.id.add_edit_image);
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        Button okBtn = findViewById(R.id.book_confirm);
        Button backBtn = findViewById(R.id.add_edit_quit);
        camera = findViewById(R.id.scan_description);
        db = FirebaseFirestore.getInstance();

        final Bundle bundle = getIntent().getExtras();
        // user has selected a book to edit if bundle if not empty
        if (bundle != null) {
            myBook = (Book) bundle.getSerializable("BOOK");   // get the item
            setTextBox(myBook);
            imgid = myBook.getImageID();

            StorageReference imageRef = storage.getReference().child(imgid);
            imageRef.getBytes(1024 * 1024)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            img.setImageBitmap(bitmap);
                        }
                    });
        }
        else{
            imgid ="default.png";
            status.setText(String.valueOf(BookStatusEnum.Available));
            StorageReference imageRef = storage.getReference().child("default.png");
            imageRef.getBytes(1024 * 1024)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            img.setImageBitmap(bitmap);
                        }
                    });
        }

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},110);
            takingPhoto();
            itot();
        }
        else {
            takingPhoto();
            itot();
        }

        DocumentReference docRef = db.collection("Users").document(GetUserFromDB.getUserID());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        userName = document.getString("userName");
                    }
                }
            }
        });

        // Confirm data and process to the database
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myBookName = bookName.getText().toString();
                final String myBookAuthor = authorName.getText().toString();
                final String myISBN = ISBN.getText().toString();
                final String myDes = description.getText().toString();
                final String myStatus = status.getText().toString();
                status.setText(myStatus);

                String old_book_id = null;
                if (myBook != null) {
                    old_book_id = myBook.getBook_id();
                }
                String myBookID = generateBookID(GetUserFromDB.getUserID(), myISBN);

                if (jg) {
                    Bitmap bm = ((BitmapDrawable) ((ImageButton) img).getDrawable()).getBitmap();
                    handleUpload(bm);
                    imgid = myBookID + ".jpeg";
                }

                if (removeImg) {
                    imgid = "default.png";

                    if (!myBook.getImageID().equals(imgid)) {
                        FirebaseStorage.getInstance()
                                .getReference()
                                .child(myBook.getImageID())
                                .delete();
                    }
                }

                myBook = new Book(imgid, myBookName, myBookAuthor, myISBN, myDes, myStatus,
                        myBookID, null, userName);

                // if user has edit the isbn code, delete the old book from database and replace it with the new one
                if (old_book_id != null && !old_book_id.equals(myBookID)) {
                    removeOldBook(old_book_id);
                }


                // validation of book data, book name, author name, and ISBN are required.
                // send data to update if valid, otherwise to display error message
                if (!(TextUtils.isEmpty(myBookName)) &&
                        !(TextUtils.isEmpty(myBookAuthor)) &&
                        !(TextUtils.isEmpty(myISBN)) &&
                        (myISBN.length() == 13)) {

                    myBook = new Book(imgid, myBookName, myBookAuthor, myISBN, myDes, myStatus,
                            myBookID, null, userName);
                    sendDataToDb(myBook);
                    Intent intent = new Intent();
                    intent.putExtra("BOOK", myBook);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    // display error in the test field if required information are not filled
                    if (TextUtils.isEmpty(myBookName)){
                        bookName.setError("Book name is required!");
                    }
                    if (TextUtils.isEmpty(myBookAuthor)){
                        authorName.setError("Author name is required!");
                    }
                    if (TextUtils.isEmpty(myISBN)){
                        ISBN.setError("ISBN is required!");
                    }
                    if (myISBN.length()!= 13){
                        ISBN.setError("ISBN must be 13 digits");
                    }
                    Toast.makeText(getApplicationContext(), "Fail. Please fill the required field",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Cancel action, no change will be made1
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                removeBookImg();
                return false;
            }
        });
    }

    /**
     * Delete the book with old book id and replace it with the new one if user has edit the isbn code
     * @param old_book_id book to be removed
     */
    public void removeOldBook(String old_book_id){
        final CollectionReference MybookRef = db.collection("Users");
        final CollectionReference LibaryRef = db.collection("Library");
        String userID = GetUserFromDB.getUserID();
        MybookRef.document(userID)
                .collection("MyBooks")
                .document(old_book_id)
                .delete();
        LibaryRef.document(old_book_id)
                .delete();
    }

    /**
     * Intent to camera
     * get the image taken by the camera to be the book image
     */
    public void takingPhoto() {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    /**
     * Intent to camera
     * get the image of image to text
     */
    public void itot() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePhotoIntent, REQUEST_IMAGE_TO_TEXT);
                }
            }
        });
    }

    /**
     * Receive result from takePhotoIntent
     * @param requestCode request for takePhotoIntent
     * @param resultCode result from takePhotoIntent
     * @param data image data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
            jg = true;
        }
        if (requestCode == REQUEST_IMAGE_TO_TEXT && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bb = (Bitmap) extras.get("data");
            getTextFromImage(bb);
        }
    }

    /**
     * To upload the book image into the data base and give the image a unique name
     * @param imageBitmap the image taken from camera
     */
    private void handleUpload(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);

        String myBookID = generateBookID(GetUserFromDB.getUserID(),ISBN.getText().toString());
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child(myBookID+".jpeg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //getDownload(reference);
                        Toast.makeText(AddEditIntent.this,"Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure", e.getCause());
                    }
                });
    }

    /**
     * Get the text in the image using the moblie vision API
     * @param bitmap the image taken from camera
     */
    private void getTextFromImage(Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "Could not get the Text", Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < items.size(); i++){
                TextBlock myItem = items.valueAt(i);
                sb.append(myItem.getValue());
                sb.append("\n");
            }
            description.setText(sb.toString());
        }
    }

    /**
     * Update data of the owned book to the database
     * @param myBook pairs of data that need to be updated to the database
     */
    public void sendDataToDb(final Book myBook) {
        final CollectionReference CollectRef = db.collection("Users");
        String userID = GetUserFromDB.getUserID();
        final String bookID = myBook.getBook_id();
        CollectRef
                .document(userID)
                .collection("MyBooks")
                .document(bookID)
                .set(myBook)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(TAG, "Book has been updated successfully!");
                        if(myBook.getStatus().equals("Available")) {
                            new AddBookToLibrary(new Share(bookID, myBook.getImageID(),
                                    myBook.getISBN(), myBook.getBook_name(), myBook.getDescription(),
                                    "Available", userName), bookID);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Book could not be updated!" + e.toString());
                    }
                });
    }

    /**
     * Generate a unique id for a book
     * @param uid unique userId from the database
     * @param isbn isbn code for the book
     * @return unique id of the book, which is uid followed by isbn
     */
    protected String generateBookID(String uid, String isbn) {
        return uid + "-" + isbn;
    }

    /**
     * Set text field with exist information
     * @param myBook a selected existing book
     */
    public void setTextBox(Book myBook){
        bookName.setText(myBook.getBook_name());
        authorName.setText(myBook.getAuthor());
        ISBN.setText(myBook.getISBN());
        description.setText(myBook.getDescription());
        status.setText(myBook.getStatus());
    }

    public void removeBookImg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to delete the image?");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                removeImg = true;
                StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("default.png");
                imageRef.getBytes(1024 * 1024)
                        .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                img.setImageBitmap(bitmap);
                            }
                        });
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}