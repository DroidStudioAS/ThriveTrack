package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.R;
public class PatchDialog extends Dialog {
    TextView dialogTitle;
    Group changeUsernameGroup;
    String mode;

    public PatchDialog(@NonNull Context context) {
        super(context);
    }
    public PatchDialog(@NonNull Context context, String mode){
        super(context);
        this.mode=mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_patch);
        /*****Start Of Ui Initializations*****/
        dialogTitle=(TextView) findViewById(R.id.dialogTitle);
        changeUsernameGroup=(Group)findViewById(R.id.changeUsernameGroup);
        /*****End Of Ui Initializations*****/


        titleSetter();
    }

    public void titleSetter(){
        switch (mode){
            case "username":
                dialogTitle.setText(R.string.change_username_label);
                break;
            case "password":
                dialogTitle.setText(R.string.change_password_label);
                break;
        }
    }
}
