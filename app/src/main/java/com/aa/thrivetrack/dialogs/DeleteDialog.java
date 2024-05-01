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

import com.aa.thrivetrack.AuthenticationActivity;
import com.aa.thrivetrack.R;
import com.aa.thrivetrack.network.NetworkHelper;
import com.aa.thrivetrack.network.SessionStorage;

import java.util.HashMap;
import java.util.Map;

public class DeleteDialog extends Dialog {

    TextView passwordEt;
    Button confirmDelete;

    private Context context;

    private final String [] PATH_TO_DELETE = new String[]{"edit","delete","user"};

    public DeleteDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_delete);
        /**Start Of Ui initializations**/
        passwordEt=(TextView) findViewById(R.id.passwordEt);
        confirmDelete=(Button) findViewById(R.id.confirmDeleteTrigger);
        /**End Of Ui initializations**/
        confirmDelete.setOnClickListener(new View.OnClickListener() {
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
        });
    }
}
