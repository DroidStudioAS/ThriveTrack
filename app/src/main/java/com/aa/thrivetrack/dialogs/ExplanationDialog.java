package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.R;



public class ExplanationDialog extends Dialog {

    String mode;

    Group modeGroup;
    Group focusInputGroup;
    Group taskInputGroup;
    Group exploreInputGroup;

    public ExplanationDialog(@NonNull Context context, String mode) {
        super(context);
        this.mode=mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_explenation);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        /**Start Of Ui Initializations**/
        modeGroup=(Group) findViewById(R.id.modeGroup);
        focusInputGroup=(Group)findViewById(R.id.focusInputGroup);
        taskInputGroup=(Group)findViewById(R.id.taskInputGroup);
        exploreInputGroup=(Group)findViewById(R.id.exoloreModeInputGroup);
        /**End Of Ui Initializations**/
        setUI();


    }

    public void setUI(){
        switch (mode){
            case "mode":
                modeGroup.setVisibility(View.VISIBLE);
                focusInputGroup.setVisibility(View.GONE);
                taskInputGroup.setVisibility(View.GONE);
                exploreInputGroup.setVisibility(View.GONE);
                break;
            case"focus-input":
                focusInputGroup.setVisibility(View.VISIBLE);
                modeGroup.setVisibility(View.GONE);
                taskInputGroup.setVisibility(View.GONE);
                exploreInputGroup.setVisibility(View.GONE);
                break;
            case "explore-input":
                exploreInputGroup.setVisibility(View.VISIBLE);
                taskInputGroup.setVisibility(View.GONE);
                focusInputGroup.setVisibility(View.GONE);
                modeGroup.setVisibility(View.GONE);
                break;
            case "tasks":
                taskInputGroup.setVisibility(View.VISIBLE);
                focusInputGroup.setVisibility(View.GONE);
                modeGroup.setVisibility(View.GONE);
                exploreInputGroup.setVisibility(View.GONE);

        }
    }
}
