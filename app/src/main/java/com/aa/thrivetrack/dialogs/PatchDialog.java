package com.aa.thrivetrack.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;

import com.aa.thrivetrack.R;
import com.aa.thrivetrack.callback.OnTaskChanged;
import com.aa.thrivetrack.callback.PatchCallback;
import com.aa.thrivetrack.models.Task;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class PatchDialog extends Dialog {
    TextView dialogTitle;
    Group changeUsernameGroup;
    //children
    EditText changeUsernameEt;
    Button confirmNewUsernameTrigger;

    Group changePasswordGroup;
    //children
    EditText newPasswordEt;
    EditText confirmPasswordEt;
    Button newPasswordTrigger;
    Group changeGoalGroup;
    //children
    EditText newGoalEt;
    Button newGoalTrigger;

    Group changeTaskGroup;
    //children
    EditText newTaskEt;
    TextView oldTaskTv;
    Button changeTaskTrigger;



    String mode;

    PatchCallback patchCallback;
    OnTaskChanged onTaskChanged;

    private final String[] PATH_TO_EDIT_USERNAME = new String[]{"edit","patch","username"};
    private final String[] PATH_TO_EDIT_PASSWORD = new String[]{"edit","patch","password"};
    private final String[] PATH_TO_EDIT_GOAL = new String[]{"edit","patch","goal"};
    private final String[] PATH_TO_EDIT_TASK = new String[]{"edit","patch","task"};


    public PatchDialog(@NonNull Context context) {
        super(context);
    }

    public PatchDialog(@NonNull Context context, String mode){
        super(context);
        this.mode=mode;
        patchCallback=(PatchCallback)context;
        if(mode.equals("task")){
            onTaskChanged=(OnTaskChanged) context;
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_patch);
        /*****Start Of Ui Initializations*****/
        dialogTitle=(TextView) findViewById(R.id.dialogTitle);

        changeUsernameGroup=(Group)findViewById(R.id.changeUsernameGroup);
        changeUsernameEt=(EditText)findViewById(R.id.newUsernameEt);
        confirmNewUsernameTrigger=(Button)findViewById(R.id.newUsernameTrigger);

        changePasswordGroup=(Group)findViewById(R.id.changePasswordGroup);
        newPasswordEt=(EditText)findViewById(R.id.newPasswordEt);
        confirmPasswordEt=(EditText)findViewById(R.id.confirmPasswordEt);
        newPasswordTrigger=(Button)findViewById(R.id.newPasswordTrigger);

        changeGoalGroup = (Group)findViewById(R.id.changeGoalGroup);
        newGoalEt = (EditText) findViewById(R.id.newGoalEt);
        newGoalTrigger=(Button)findViewById(R.id.changeGoalTrigger);

        changeTaskGroup = (Group) findViewById(R.id.changeTaskGroup);
        oldTaskTv = (TextView)findViewById(R.id.oldTaskTv);
        newTaskEt = (EditText)findViewById(R.id.newTaskEt);
        changeTaskTrigger = (Button)findViewById(R.id.changeTaskTrigger);
        /*****End Of Ui Initializations*****/
        /*****Start Of OnClickListeners*****/
        confirmNewUsernameTrigger.setOnClickListener(changeUsername());
        newPasswordTrigger.setOnClickListener(changePassword());
        newGoalTrigger.setOnClickListener(changeGoal());
        changeTaskTrigger.setOnClickListener(changeTask());
        /*****End Of OnClickListeners*****/

        dialogSetter();
    }

    public void dialogSetter(){
        switch (mode){
            case "username":
                dialogTitle.setText(R.string.change_username_label);
                changeUsernameGroup.setVisibility(View.VISIBLE);
                changePasswordGroup.setVisibility(View.GONE);
                changeGoalGroup.setVisibility(View.GONE);
                changeTaskGroup.setVisibility(View.GONE);
                break;
            case "password":
                dialogTitle.setText(R.string.change_password_label);
                changePasswordGroup.setVisibility(View.VISIBLE);
                changeUsernameGroup.setVisibility(View.GONE);
                changeGoalGroup.setVisibility(View.GONE);
                changeTaskGroup.setVisibility(View.GONE);
                break;
            case "goal":
                dialogTitle.setText("Change Goal:");
                newGoalEt.setText(SessionStorage.getUserData().getGoal());
                changeGoalGroup.setVisibility(View.VISIBLE);
                changePasswordGroup.setVisibility(View.GONE);
                changeUsernameGroup.setVisibility(View.GONE);
                changeTaskGroup.setVisibility(View.GONE);
                break;
            case "task":
                dialogTitle.setText("Change Task:");
                oldTaskTv.setText(SessionStorage.getTaskToEdit().getTaskText());
                changeTaskGroup.setVisibility(View.VISIBLE);
                changeGoalGroup.setVisibility(View.GONE);
                changePasswordGroup.setVisibility(View.GONE);
                changeUsernameGroup.setVisibility(View.GONE);

        }
    }
    public View.OnClickListener changeUsername(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = changeUsernameEt.getText().toString().trim();
                if(newUsername.equals("")){
                    Toast.makeText(getContext(), "Fill Out All Info", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("old-username", SessionStorage.getUsername());
                params.put("new-username",newUsername);

                NetworkHelper.callPatch(PATH_TO_EDIT_USERNAME, params, 0);
                NetworkHelper.waitForReply();
                Log.i("response", SessionStorage.getServerResponse());
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(), "Username Changed", Toast.LENGTH_SHORT).show();
                    SessionStorage.setUsername(newUsername);
                    patchCallback.onChange(newUsername);
                    dismissDialog();
                }else if(SessionStorage.getServerResponse().equals("false")){
                    Toast.makeText(getContext(), "Username Already In Use", Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();
            }
        };
    }
    public View.OnClickListener changePassword(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEt.getText().toString().trim();
                String confirmedPassword = confirmPasswordEt.getText().toString().trim();

                if(newPassword.equals("") || confirmedPassword.equals("")){
                    Toast.makeText(getContext(), "Fill It All Out", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!newPassword.equals(confirmedPassword)){
                    Toast.makeText(getContext(), "Passwords Dont Match", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("username", SessionStorage.getUsername());
                params.put("password", newPassword);

                NetworkHelper.callPatch(PATH_TO_EDIT_PASSWORD, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                    dismissDialog();
                }else if(SessionStorage.getServerResponse().equals("false")){
                    Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();


            }
        };
    }
    public View.OnClickListener changeGoal(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newGoal = newGoalEt.getText().toString().trim();
                if(newGoalEt.equals("")||newGoal.equals(SessionStorage.getUserData().getGoal())){
                    Toast.makeText(getContext(), "Fill Out All Info", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("user-id",String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("goal", newGoal);
                NetworkHelper.callPatch(PATH_TO_EDIT_GOAL,params,0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    SessionStorage.getUserData().setGoal(newGoal);
                    Toast.makeText(getContext(), "Goal Changed", Toast.LENGTH_SHORT).show();
                    patchCallback.onChange(newGoal);
                    dismissDialog();
                }else{
                    Toast.makeText(getContext(), "Oops", Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();

            }
        };
    }
    public View.OnClickListener changeTask(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldTaskText = SessionStorage.getTaskToEdit().getTaskText().trim();
                String newTaskText = newTaskEt.getText().toString().trim();
                //validate
                if(newTaskText.equals("") || newTaskText.equals(oldTaskText)){
                    Toast.makeText(getContext(),"Oops", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("user-id",String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("task-text",oldTaskText);
                params.put("new-text", newTaskText);

                NetworkHelper.callPatch(PATH_TO_EDIT_TASK, params, 0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(),"Task Updated", Toast.LENGTH_SHORT).show();
                    for(Task x : SessionStorage.getUserData().getTasks()){
                        if(x.getTaskText().equals(oldTaskText)){
                            x.setTaskText(newTaskText);
                        }
                        onTaskChanged.onTaskChange(newTaskText);
                        dismissDialog();
                    }
                }else{
                    Toast.makeText(getContext(),"Oops", Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();


            }
        };
    }


    public void dismissDialog(){
        this.dismiss();
    }
}
