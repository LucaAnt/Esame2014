package com.trieste.mobile.esame2014alberghi.ui.elencoalberghi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentDeleteConfirmation extends DialogFragment {

    private String title, message;
    private long toDoId;
    private ConfirmDialogFragmentListener listener;

    public DialogFragmentDeleteConfirmation(String title, String message, long toDoId) {
        this.title = title;
        this.message = message;
        this.toDoId = toDoId;
    }

    @Override
    public void onAttach(Activity activity) {
        if (activity instanceof ConfirmDialogFragmentListener) {
            listener = (ConfirmDialogFragmentListener) activity;
        } else {
            listener = null;
        }
        super.onAttach(activity);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPositivePressed(toDoId);
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onNegativePressed();

            }
        });
        return dialog.create();
    }


    public interface ConfirmDialogFragmentListener
    {

        void onNegativePressed();

        void onPositivePressed(long id);
    }
}
