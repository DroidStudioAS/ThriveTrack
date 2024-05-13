package com.aa.thrivetrack.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.AuthenticationActivity;
import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnDeleteTask;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;
import com.aa.thrivetrack.validation.ToastFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteDialog extends Dialog {

    TextView deleteTitle;

    Group deleteUserGroup;
    //children
    TextView passwordEt;
    Button confirmUserDelete;
    String mode;

    Group deleteTaskGroup;
    TextView deleteTaskTv;
    Button confirmTaskDelete;

    private Context context;
    OnDeleteTask onDelete;

    private final String [] PATH_TO_DELETE_USER = new String[]{"edit","delete","user"};
    private final String [] PATH_TO_DELETE_TASK = new String[]{"edit","delete","task"};


    public DeleteDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    public DeleteDialog(@NonNull Context context, String mode) {
        super(context);
        this.context=context;
        this.mode=mode;
        if(mode.equals("task")){
            onDelete=(OnDeleteTask) context;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);
        /**Start Of Ui initializations**/
        deleteTitle=(TextView)findViewById(R.id.deleteTitle);

        deleteUserGroup = (Group) findViewById(R.id.deleteUserGroup);
        passwordEt=(TextView) findViewById(R.id.passwordEt);
        confirmUserDelete =(Button) findViewById(R.id.confirmDeleteTrigger);

        deleteTaskGroup=(Group)findViewById(R.id.deleteTaskGroup);
        deleteTaskTv=(TextView)findViewById(R.id.taskToDeleteTv);
        confirmTaskDelete=(Button)findViewById(R.id.confirmTaskDelete);
        /**End Of Ui initializations**/
        confirmUserDelete.setOnClickListener(deleteUser());
        confirmTaskDelete.setOnClickListener(deleteTask());

        setDialog();
    }
    public void setDialog(){
        switch (mode){
            case "user":
                deleteTitle.setText("Delete User");
                deleteUserGroup.setVisibility(View.VISIBLE);
                deleteTaskGroup.setVisibility(View.GONE);
                break;
            case "task":
                deleteTitle.setText("Delete Task");
                deleteTaskTv.setText(SessionStorage.getTaskToEdit().getTaskText());
                deleteTaskGroup.setVisibility(View.VISIBLE);
                deleteUserGroup.setVisibility(View.GONE);

        }
    }


    public View.OnClickListener deleteUser(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEt.getText().toString().trim();
                if(password.equals("")){
                   ToastFactory.showToast(context, "You Must Enter A Password");
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("user-id",String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("password", password);

                NetworkHelper.callDelete(PATH_TO_DELETE_USER,params,0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    ToastFactory.showToast(context, "Account Deleted");
                    context.startActivity(new Intent(context, AuthenticationActivity.class));
                }else{
                    ToastFactory.showToast(context, "Wrong Password");
                }
                SessionStorage.resetServerResponse();

            }
        };
    }
    public View.OnClickListener deleteTask(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("user-id", String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("task-text",SessionStorage.getTaskToEdit().getTaskText());
                NetworkHelper.callDelete(PATH_TO_DELETE_TASK, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(),"Task Deleted",Toast.LENGTH_SHORT).show();
                    Task toRemove = new Task();
                    for(Task x : SessionStorage.getUserData().getTasks()){
                        if(x.getTaskText().equals(SessionStorage.getTaskToEdit().getTaskText())){
                            toRemove=x;
                        }
                    }
                    SessionStorage.getUserData().getTasks().remove(toRemove);
                    onDelete.onTaskDeleted();
                }else{
                    Toast.makeText(getContext(),"Oops",Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();
            }
        };
    }

}
