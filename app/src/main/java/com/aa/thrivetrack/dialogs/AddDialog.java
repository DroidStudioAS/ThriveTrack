package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnAddTask;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class AddDialog extends Dialog {
    String mode;
    OnAddTask onAddTask;

    TextView addTitle;

    Group addTaskGroup;
    EditText addTaskEt;
    TextView confirmTaskInput;

    private static final String[] PATH_TO_ADD_TASK = new String[]{"write", "task"};

    public AddDialog(@NonNull Context context, String mode) {
        super(context);
        this.mode=mode;
        if(mode.equals("task")){
            onAddTask=(OnAddTask) context;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add);
        /**Start Of Ui Initializations**/
        addTitle=(TextView)findViewById(R.id.addTtitle);

        addTaskGroup=(Group)findViewById(R.id.addTaskGroup);
        addTaskEt=(EditText) findViewById(R.id.addTaskEt);
        confirmTaskInput=(TextView) findViewById(R.id.confirmAddTask);
        /**End Of Ui Initializations**/
        confirmTaskInput.setOnClickListener(addTask());

        setDialog();
    }

    public void setDialog(){
        switch (mode){
            case "task":
                addTitle.setText("Add Task");
                addTaskGroup.setVisibility(View.VISIBLE);
        }
    }
    public View.OnClickListener addTask(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTask = addTaskEt.getText().toString().trim();
                if(newTask.equals("")){
                    Toast.makeText(getContext(),"fill out",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("task-text", newTask);

                NetworkHelper.callPost(PATH_TO_ADD_TASK, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(),"Task Added",Toast.LENGTH_SHORT).show();
                    SessionStorage.getUserData().getTasks().add(new Task(newTask));
                    onAddTask.onTaskAdded();
                }else{
                    Toast.makeText(getContext(),"Oops",Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();

            }
        };
    }
}
