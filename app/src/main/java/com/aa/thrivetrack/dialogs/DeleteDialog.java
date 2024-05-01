package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.aa.thrivetrack.R;

public class DeleteDialog extends Dialog {

    public DeleteDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);
    }
}
