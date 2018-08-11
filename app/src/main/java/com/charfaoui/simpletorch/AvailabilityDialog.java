package com.charfaoui.simpletorch;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * @definition this class extends the Dialog fragment
 * which will be used to show the user a dialog indicating
 * that this phone does not provides the flash features.
 */
public class AvailabilityDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //create a builder object for the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //setting the message and the title of dialog
        builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    getActivity().finish();
                })
                .setOnCancelListener(dialog -> getActivity().finish());

        //creating and returning the dialog
        return builder.create();
    }
}
