package com.example.a301pro;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.fragment.app.DialogFragment;


public class ComfirmDialog extends DialogFragment
{
    //private String[] mEvaluteVals = new String[] { "GOOD", "BAD", "NORMAL" };
    //public static final String RESPONSE_EVALUATE = "response_evaluate";
    private OnFragmentInteractionListenerComfirm listener;

    private Book book;

    public ComfirmDialog(Book book) {
        this.book = book;
    }

    public interface OnFragmentInteractionListenerComfirm{
        void onOkPressed(Book book);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do You Really Want To Delet This Item").setNegativeButton("Cancel",null).setPositiveButton("ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onOkPressed(book);
            }
        });
        return builder.create();
    }


}
