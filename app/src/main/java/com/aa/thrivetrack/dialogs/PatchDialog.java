package com.aa.thrivetrack.dialogs;

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
import com.aa.thrivetrack.callback.PatchCallback;
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
    String mode;

    PatchCallback patchCallback;

    private final String[] PATH_TO_EDIT_USERNAME = new String[]{"edit","patch","username"};

    public PatchDialog(@NonNull Context context) {
        super(context);
    }
    public PatchDialog(@NonNull Context context, String mode){
        super(context);
        this.mode=mode;
        patchCallback=(PatchCallback)context;
    }

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
        /*****End Of Ui Initializations*****/
        /*****Start Of OnClickListeners*****/
        confirmNewUsernameTrigger.setOnClickListener(changeUsername());
        /*****End Of OnClickListeners*****/

        dialogSetter();
    }

    public void dialogSetter(){
        switch (mode){
            case "username":
                dialogTitle.setText(R.string.change_username_label);
                changeUsernameGroup.setVisibility(View.VISIBLE);
                changePasswordGroup.setVisibility(View.GONE);
                break;
            case "password":
                dialogTitle.setText(R.string.change_password_label);
                changePasswordGroup.setVisibility(View.VISIBLE);
                changeUsernameGroup.setVisibility(View.GONE);
                break;
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
                    patchCallback.onUsernameChanged(newUsername);
                    dismissDialog();
                }else if(SessionStorage.getServerResponse().equals("false")){
                    Toast.makeText(getContext(), "Username Already In Use", Toast.LENGTH_SHORT).show();
                }
                SessionStorage.resetServerResponse();
            }
        };
    }

    public void dismissDialog(){
        this.dismiss();
    }
}
