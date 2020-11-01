package com.example.a301pro;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a301pro.Utilities.AddBookToLibrary;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.a301pro.Utilities.AddBookToLibrary;
import java.util.HashMap;


/**
 * This class provides functionality of adding a new book to the collection of the user,
 * and editing the data of a selected own book.
 *
 * current process
 * success:
 * 可以add/edit, 可以同步到数据库
 *
 * fail:
 * add 的拍照功能, 和图片上传功能还没弄
 *
 * bug:
 * onActivityResult()里的resultCode 有问题, 导致从addEdit点击Back返回出现闪退
 */
public class AddEditIntent extends AppCompatActivity {
    private EditText bookName, authorName, ISBN, description;
    private TextView status;
    private ImageButton img;
    public static final String TAG = "AddEdit";
    protected FirebaseFirestore db;
    private int myPos;
    private Book myBook;
    private String userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

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
        Button okBtn = findViewById(R.id.book_confirm);
        Button backBtn = findViewById(R.id.add_edit_quit);
        Button pickStatus = findViewById(R.id.pick_status);
        Button camera = findViewById(R.id.scan_description);
        db = FirebaseFirestore.getInstance();
        final CollectionReference ColRef = db.collection("Mybook");

        Bundle bundle = getIntent().getExtras();
        // user has selected a book to edit if bundle if not empty
        if(bundle != null) {
            myBook = (Book) bundle.getSerializable("BOOK");   // get the item
            myPos = bundle.getInt("POS");
            setTextBox(myBook);
        }

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

        // Confirm data and process to the database
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String myBookName = bookName.getText().toString();
                final String myBookAuthor = authorName.getText().toString();
                final String myISBN = ISBN.getText().toString();
                final String myDes = description.getText().toString();
                final String myStatus = status.getText().toString();
                final int myImg = R.drawable.ic_image1; // sample image*********

                // validation of book data, book name, author name, and ISBN are required.
                // send data to update if valid, otherwise to display error message
                if(!(TextUtils.isEmpty(myBookName)) &&
                        !(TextUtils.isEmpty(myBookAuthor)) &&
                        !(TextUtils.isEmpty(myISBN))) {

//                    HashMap<String, Object> data = new HashMap<>();
//                    data.put("Book Name", myBookName);
//                    data.put("Author Name", myBookAuthor);
//                    data.put("ISBN", myISBN);
//                    data.put("Description", myDes);
//                    data.put("Status", myStatus);
//                    data.put("Image", myImg);
//                    sendDataToDb(data);

                    String myBookID = generateBookID(getUserID(), myISBN);
                    myBook = new Book(myImg, myBookName, myBookAuthor, myISBN, myDes, myStatus, myBookID, null, userName);

                    sendDataToDb(myBook);

                    Intent intent = new Intent();
                    intent.putExtra("BOOK", myBook);
                    intent.putExtra("POS", myPos);
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
                    Toast.makeText(getApplicationContext(), "Fail. Please fill the required field",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Select a status from status list
        pickStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStatusMenu(view);
            }
        });

        // Cancel action, no change will be made
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }


    /**
     * Update data of the owned book to the database
     * @param myBook pairs of data that need to be updated to the database
     */
    public void sendDataToDb(final Book myBook) {
        final CollectionReference CollectRef = db.collection("Users");
        String userID = getUserID();
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
                        new AddBookToLibrary(new Share(myBook.getImageID(), myBook.getBook_name(), myBook.getDescription(), "Available", myBook.getOwner()), bookID);
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
     * get uid of the current logged in user
     * @return uid as a string
     */
    protected String getUserID() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     * generate a unique id for a book
     * @param uid unique userId from the database
     * @param isbn isbn code for the book
     * @return unique id of the book, which is uid followed by isbn
     */
    protected String generateBookID(String uid, String isbn) {
        return uid +"-"+isbn;
    }

    /**
     * set text field with exist information
     * @param myBook a selected existing book
     */
    public void setTextBox(Book myBook){
        bookName.setText(myBook.getBook_name());
        authorName.setText(myBook.getAuthor());
        ISBN.setText(myBook.getISBN());
        description.setText(myBook.getDescription());
        status.setText(myBook.getStatus());
    }

    /**
     * popup the menu for picking status
     * @param view view
     */
    public void showStatusMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.filter_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                status.setText(item.getTitle());
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }


}
