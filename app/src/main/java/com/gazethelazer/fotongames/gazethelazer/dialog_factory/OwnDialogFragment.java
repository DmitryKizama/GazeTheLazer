package com.gazethelazer.fotongames.gazethelazer.dialog_factory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.gazethelazer.fotongames.gazethelazer.R;
import com.gazethelazer.fotongames.gazethelazer.activity.MainPage;

public class OwnDialogFragment extends DialogFragment {

    public static void showErrorDialog(Context context, String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(description)
                .setPositiveButton("��",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        builder.create().show();
    }

}
