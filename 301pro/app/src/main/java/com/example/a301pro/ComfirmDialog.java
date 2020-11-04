package com.example.a301pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This class show the alert window when user tries to delete a book, to prevent accidental deletion
 */
public class ComfirmDialog extends DialogFragment
{
    private Book book;
    private CollectionReference collectRef;
    final String TAG = "Delete";

    /**
     * constructor of ComfirmDialog
     * @param book book data passed from AddEditIntent
     * @param collectionReference corresponding collection reference in the database
     */
    public ComfirmDialog(Book book, CollectionReference collectionReference) {
        this.book = book;
        this.collectRef = collectionReference;
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
                collectRef
                        .document(String.valueOf(book.getBook_id()))
                        .delete()  // delete selected item from database
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Data has been deleted successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "Data could not be deleted!" + e.toString());
                            }
                        });
            }
        });
        return builder.create();
    }
}