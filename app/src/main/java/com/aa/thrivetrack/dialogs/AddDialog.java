package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.R;

public class AddDialog extends Dialog {
    String mode;

    TextView addTitle;

    Group addTaskGroup;
    EditText addTaskEt;
    Button confirmTaskInput;

    public AddDialog(@NonNull Context context, String mode) {
        super(context);
        this.mode=mode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add);
        /**Start Of Ui Initializations**/
        addTitle=(TextView)findViewById(R.id.addTtitle);

        addTaskGroup=(Group)findViewById(R.id.addTaskGroup);
        addTaskEt=(EditText) findViewById(R.id.addTaskEt);
        confirmTaskInput=(Button) findViewById(R.id.confirmAddTask);
        /**End Of Ui Initializations**/

        setDialog();
    }

    public void setDialog(){
        switch (mode){
            case "task":
                addTitle.setText("Add Task");
                addTaskGroup.setVisibility(View.VISIBLE);
        }
    }
}
