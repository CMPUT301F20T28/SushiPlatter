package com.example.a301pro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.a301pro.Models.Book;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;

/**
 * This class show the alert window when user tries to delete a book, to prevent accidental deletion
 */
public class ComfirmDialog extends DialogFragment
{
    private Book book;
    private CollectionReference collectRef;
    private  CollectionReference LibraryReference;
    final String TAG = "Delete";

    /**
     * constructor of ComfirmDialog
     * @param book book data passed from AddEditIntent
     * @param collectionReference corresponding collection reference in the database
     */
    public ComfirmDialog(Book book, CollectionReference collectionReference,
                         CollectionReference LibraryReference) {
        this.book = book;
        this.collectRef = collectionReference;
        this.LibraryReference = LibraryReference;
    }

    /**
     * Interface for positive action
     */
    public interface OnFragmentInteractionListenerComfirm{
        void onOkPressed(Book book);
    }

    /**
     * Build the alert dialog for the user to prevent accidental deletion
     * @param savedInstanceState data of previous instance of the dialog
     * @return dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // customize the alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are you sure to delete\"" + book.getBook_name() + "\"?")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Yes", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (book.getStatus().contentEquals("Available")) {
                            collectRef
                                    .document(String.valueOf(book.getBook_id()))
                                    .delete()  // delete selected item from database
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Book deleted");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Book could not be deleted!" + e.toString());
                                        }
                                    });

                            LibraryReference
                                    .document(String.valueOf(book.getBook_id()))
                                    .delete()  // delete selected item from database
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "Book in library has been deleted successfully!");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "Book in library could not be deleted!" + e.toString());
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(getContext(), "Fail, only the Available book can be deleted!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }
}