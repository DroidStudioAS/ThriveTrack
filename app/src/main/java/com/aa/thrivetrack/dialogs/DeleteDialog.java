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
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class DeleteDialog extends Dialog {

    TextView deleteTitle;

    Group deleteUserGroup;
    //children
    TextView passwordEt;
    Button confirmUserDelete;
    String mode;

    private Context context;

    private final String [] PATH_TO_DELETE = new String[]{"edit","delete","user"};

    public DeleteDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    public DeleteDialog(@NonNull Context context, String mode) {
        super(context);
        this.context=context;
        this.mode=mode;
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
        /**End Of Ui initializations**/
        confirmUserDelete.setOnClickListener(deleteUser());

        setDialog();
    }

    public View.OnClickListener deleteUser(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEt.getText().toString().trim();
                if(password.equals("")){
                    Toast.makeText(getContext(),"Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String,String> params = new HashMap<>();
                params.put("user-id",String.valueOf(SessionStorage.getUserData().getUser().getUser_id()));
                params.put("password", password);

                NetworkHelper.callDelete(PATH_TO_DELETE,params,0);
                NetworkHelper.waitForReply();
                if(SessionStorage.getServerResponse().equals("true")){
                    Toast.makeText(getContext(),"Account Deleted", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, AuthenticationActivity.class));
                }
                SessionStorage.resetServerResponse();

            }
        };
    }

    public void setDialog(){
        switch (mode){
            case "user":
                deleteTitle.setText("Delete User");
                deleteUserGroup.setVisibility(View.VISIBLE);

        }
    }
}
